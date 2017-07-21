package com.touchingcode.gpx.androidgpxframework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * It's original class made by Yoshiki Kuno.
 * This class is used to remove unnecessary TrackPoint from root GPX-File
 *
 * @author Yoshiki Kuno
 */
class GPXSmoother {

    private static Double DEFAULT_DIRECTION_THRESHOLD = 8.0; // in degree
    private static Double DEFAULT_DISTANCE_THRESHOLD = 50.0; // in meter
    private static Double MINIMUM_EFFICIENT = 0.3; // percentage 30 % -> 0.3


    
    private static double lostDistance;
    private static int totalTrackPoints = 0;

    /**
     * Track points reducing by cardinal direction and distance between two coordinates.
     * If the track points reducing is not enough efficient, the method will be called automatic again with new threshold values.
     * Threshold Values are adjusted for the GPX files of the canoe.
     *
     * @param tracks ArrayList<GPXTrack>
     * @return newTracks
     */
    static ArrayList<GPXTrack> removeUnnecessaryTrackPoints(ArrayList<GPXTrack> tracks){
        getNumberOfTrackPoints(tracks);
        return removeUnnecessaryTrackPoints(tracks, DEFAULT_DIRECTION_THRESHOLD, DEFAULT_DISTANCE_THRESHOLD);
    }

    private static ArrayList<GPXTrack> removeUnnecessaryTrackPoints(ArrayList<GPXTrack> tracks, double directionThreshold, double distanceThreshold){

        ArrayList<GPXTrack> newTracks = new ArrayList<>();
        int totalCount = 0;

        for (GPXTrack track: tracks) {
            GPXTrack newTrack = new GPXTrack();

            for (GPXTrackSegment segment: track.getTracksegments()) {
                GPXTrackSegment newSegments = new GPXTrackSegment();
                ArrayList<GPXTrackPoint> pointsArray = segment.getTrackpoints();

                int count = 0;
                int index = 0;
                double directionBefore = 0;

                for (GPXTrackPoint point: pointsArray) {

                    if(index == 0 || index + 1 == pointsArray.size()){
                        count = addTrackPoint(count, newSegments, point);
                    } else {
                        double direction = getCardinalDirectionFromTwoPoints(point, pointsArray.get(index + 1));
                        double distance = getDistanceOfTwoPoints(point, pointsArray.get(index + 1));

                        long timeInSecond = getTimeDifferenceOfTwoPoints(point, pointsArray.get(index + 1));
                        double elevationDifInSec = getElevationDifferenceOfTwoPoints(point, pointsArray.get(index + 1), timeInSecond);
//                        double speed = getSpeedFromDistanceAndTime(distance, timeInSecond);

                        // It's only for test
//                        printOutInformation(index, point, distance, direction, elevationDifInSec, timeInSecond, speed);

                        double directionChange = direction - directionBefore;
                        if (directionChange > directionThreshold || directionChange < -directionThreshold){
                            count = addTrackPoint(count, newSegments, point);
                        } else {
                            lostDistance += distance;
                            if(lostDistance > distanceThreshold){
                                count = addTrackPoint(count, newSegments, point);
                            }
                        }
                        directionBefore = direction;
                    }
                    index++;
                }
                totalCount += count;
                newTrack.addTracksegment(newSegments);
            }
            newTracks.add(newTrack);
        }
        if((double)totalCount / (double)totalTrackPoints > MINIMUM_EFFICIENT){
            return removeUnnecessaryTrackPoints(tracks, directionThreshold + 3.0, distanceThreshold + 40.0);
        }
        return newTracks;
    }

    private static int addTrackPoint(int count, GPXTrackSegment newSegments, GPXTrackPoint point) {
        newSegments.addTrackpoint(point);
        count++;
        lostDistance = 0;
        return count;
    }

    private static void getNumberOfTrackPoints(ArrayList<GPXTrack> tracks) {
        for (GPXTrack trackA: tracks) {
            for (GPXTrackSegment segmentA : trackA.getTracksegments()) {
                ArrayList<GPXTrackPoint> pointsArrayA = segmentA.getTrackpoints();
                totalTrackPoints += pointsArrayA.size();
            }
        }
    }

    private static void printOutInformation(int index, GPXTrackPoint point, double distance, double direction, double elevationDifInSec, long timeInSecond, double speed) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' T 'HH:mm:ss", Locale.US);
        if(point.hasTime()) {
            System.out.println("TP" + index + " " + df.format(point.getTime()));
        }
        System.out.println("Distance : "  + distance);
        System.out.println("Direction : "  + direction);
        System.out.println("Elevation Difference in sec: " + elevationDifInSec);
        System.out.println("Time : " + timeInSecond);
        System.out.println("Speed : " + speed + " km/S");
        System.out.println("---\n");
    }

    private static double getDistanceOfTwoPoints(GPXTrackPoint firstPoint, GPXTrackPoint secondPoint){

        double EARTH_RADIUS = 6371000;
        double dLat = Math.toRadians(secondPoint.getLatitude() - firstPoint.getLatitude());
        double dLng = Math.toRadians(secondPoint.getLongitude() - firstPoint.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(firstPoint.getLatitude())) * Math.cos(Math.toRadians(secondPoint.getLatitude())) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private static Double getCardinalDirectionFromTwoPoints(GPXTrackPoint firstPoint, GPXTrackPoint secondPoint){

        double dLon = (secondPoint.getLongitude() - firstPoint.getLongitude());
        double y = Math.sin(dLon) * Math.cos(secondPoint.getLatitude());
        double x = Math.cos(firstPoint.getLatitude()) * Math.sin(secondPoint.getLatitude()) - Math.sin(firstPoint.getLatitude())
                * Math.cos(secondPoint.getLatitude()) * Math.cos(dLon);
        double bearing = Math.atan2(y, x);

        bearing = Math.toDegrees(bearing);
        bearing = (bearing + 360) % 360;
        bearing = 360 - bearing;

        return bearing;
    }

    private static double getElevationDifferenceOfTwoPoints(GPXTrackPoint firstPoint, GPXTrackPoint secondPoint, long time){

        if(!firstPoint.hasElevation() || !secondPoint.hasElevation() || time == 0) return 0;

        return (secondPoint.getElevation() - firstPoint.getElevation()) / time;
    }

    private static long getTimeDifferenceOfTwoPoints(GPXTrackPoint firstPoint, GPXTrackPoint secondPoint){

        if(!firstPoint.hasTime() || !secondPoint.hasTime()) return 0;

        return (secondPoint.getTime().getTime() - firstPoint.getTime().getTime()) / 1000;
    }

    private static double getSpeedFromDistanceAndTime(double distance, long timeInSecond){

        if(distance == 0 || timeInSecond == 0) return 0;

        double distanceKiloMeter = distance / 1000;
        double timeInHour = (double)timeInSecond / (double)3600;

        return distanceKiloMeter / timeInHour;
    }
}