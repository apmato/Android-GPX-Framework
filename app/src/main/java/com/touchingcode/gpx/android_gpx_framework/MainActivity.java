package com.touchingcode.gpx.android_gpx_framework;

import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.touchingcode.gpx.androidgpxframework.GPXMetadata;
import com.touchingcode.gpx.androidgpxframework.GPXParser;
import com.touchingcode.gpx.androidgpxframework.GPXRoot;
import com.touchingcode.gpx.androidgpxframework.GPXTrack;
import com.touchingcode.gpx.androidgpxframework.GPXTrackPoint;
import com.touchingcode.gpx.androidgpxframework.GPXTrackSegment;
import com.touchingcode.gpx.androidgpxframework.GPXType;
import com.touchingcode.gpx.androidgpxframework.GPXWaypoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * This class shows example usage of Android-GPX-Framework
 *
 * @author Yoshiki Kuno
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // You need app permissions to access your storage. Modify also your android manifest file.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        String rootPath = Environment.getExternalStorageDirectory().toString() + "/gpx/";

        /*
        This is an example, which shows how to create a gpx object with this framework
         */
        GPXRoot root = new GPXRoot();
        root.rootWithCreator("Touching Code GmbH");

        GPXMetadata metadata = new GPXMetadata();
        metadata.setName("sample gpx file");
        root.setMetadata(metadata);

        GPXWaypoint wp1 = GPXWaypoint.waypointWithLatitude(47.11478, 9.253538);
        wp1.setName("Tokyo Tower");
        wp1.setComment("The old TV tower in Tokyo.");
        wp1.newLinkWithHref("Data/Location3152-1");
        root.addWaypoint(wp1);

        GPXTrack track = root.newTrack();

        GPXTrackPoint point01 = track.newTrackpointWithLatitude(52.486428, 13.392954);
        point01.setElevation(47.310669);
        point01.setTime(GPXType.dateTime("2017-05-31T12:15:59Z"));

        GPXTrackPoint point02 = track.newTrackpointWithLatitude(52.486607, 13.393025);
        point02.setElevation(52.635071);
        point02.setTime(GPXType.dateTime("2017-05-31T12:16:00Z"));

        // if you want to make new track segment
        GPXTrackSegment newSegment = track.newTrackSegment();
        GPXTrackPoint point11 = newSegment.newTrackpointWithLatitude(52.486860, 13.394291);
        point11.setElevation(50.100830);
        point11.setTime(GPXType.dateTime("2017-05-31T12:17:24Z"));

        // generateGPX() method will create GPX-formatted String from GPXRoot-Object
        String xml = root.generateGPX();
//        root.removeUnnecessaryTrackPoints();


        System.out.println(xml);

//        String newFilename = "NewGPX";
//        File newFile = new File (rootPath, newFilename + ".gpx");
//        if(newFile.exists()){
//            newFile.delete();
//        }
//        FileOutputStream outputStream;
//        try {
//            outputStream = new FileOutputStream(newFile);
//            outputStream.write(xml.getBytes());
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /*
        Read existing GPX-file from mobile storage
        (use GPXParser class. There are 3 methods to reading XML depending on source types)
        Create 'gpx' folder to your root directory on your device and locate a gpx file there
         */
//        String filename = "track_big";
//        String path = rootPath + filename + ".gpx";
//        String xml = "";
//        try {
//            File file = new File(path);
//            URI uri = file.toURI();
//            URL url = uri.toURL();
//            GPXRoot rootObject = GPXParser.parseGPXAtURL(url);
////            rootObject.removeUnnecessaryTrackPoints();
//            xml = rootObject.generateGPX();
//
//            System.out.println(xml);
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//
//        String newFilename = filename + "_smooth5";
//        File newFile = new File (rootPath, newFilename + ".gpx");
//        if(newFile.exists()){
//            newFile.delete();
//        }
//        FileOutputStream outputStream;
//        try {
//            outputStream = new FileOutputStream(newFile);
//            outputStream.write(xml.getBytes());
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
