package com.jeremydyer.hdpmvn.service;

import com.jeremydyer.hdpmvn.core.HDFRelease;
import com.jeremydyer.hdpmvn.core.HDPRelease;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * InMemory database of Hortonworks releases
 */
public class HortonworksReleaseDBService {

    private Map<String, HDPRelease> hdpReleases = new HashMap<String, HDPRelease>();
    private Map<String, HDFRelease> hdfReleases = new HashMap<String, HDFRelease>();

    private String dbPath;

    public HortonworksReleaseDBService(String dbPath) {
        this.dbPath = dbPath;
    }

    public HDPRelease hdpReleaseForVersion(String version) {
        return hdpReleases.get(version);
    }

    public void load() {
        XStream xStream = new XStream();
        try {
            hdpReleases = (Map<String, HDPRelease>) xStream.fromXML(new File(dbPath + File.separator + "hdp.xml"));
            hdfReleases = (Map<String, HDFRelease>) xStream.fromXML(new File(dbPath + File.separator + "hdf.xml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        XStream xStream = new XStream();
        try {
            xStream.toXML(hdpReleases, new FileOutputStream(new File(dbPath + File.separator + "hdp.xml")));
            xStream.toXML(hdfReleases, new FileOutputStream(new File(dbPath + File.separator + "hdf.xml")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addHDPRelease(HDPRelease hdpRelease) {
        hdpReleases.put(hdpRelease.getHdpVersion(), hdpRelease);
    }
}
