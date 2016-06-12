package org.sleuthkit.autopsy.casemodule;

import java.util.LinkedList;
import java.util.List;
import org.openide.util.Exceptions;
import org.sleuthkit.datamodel.SleuthkitJNI;
import org.sleuthkit.datamodel.TskCoreException;


class VolumeMetaData {

    private static int AMOUNT_PARTITIONS = 4;
    
    private String path;
    private long partitionNumber;
    private long offSet;
    private byte[] firstSector;

    public long getOffSet() {
        return offSet;
    }

    public long getPartitionNumber() {
        return partitionNumber;
    }

    public byte[] getFirstSector() {
        return firstSector;
    }

    public String getPath() {
        return path;
    }

    public void setOffSet(long offset) {
        this.offSet = offset;
    }

    public void setPartitionNumber(long pn) {
        this.partitionNumber = pn;
    }

    public void setFirstSector(byte[] sector) {
        this.firstSector = sector;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static List<VolumeMetaData> getVolumeMetaData(String image) {
        String[] imageArray = {image};

        List<VolumeMetaData> result = new LinkedList<>();

        try {
            long handle = SleuthkitJNI.openImage(imageArray);
            long vsHandle = SleuthkitJNI.openVs(handle, 0);

            for (int volumeIndex = 0; volumeIndex < AMOUNT_PARTITIONS; volumeIndex++) {
                VolumeMetaData volumeMetaData = new VolumeMetaData();
                volumeMetaData.setPartitionNumber(volumeIndex);

                volumeMetaData.setOffSet(SleuthkitJNI.getVolOffset(vsHandle, volumeIndex));

                long volumePointer = SleuthkitJNI.openVsPart(vsHandle, volumeIndex);

                byte readBuffer[] = new byte[512];
                long c = SleuthkitJNI.readVsPart(volumePointer, readBuffer, 0, 512);
                volumeMetaData.setFirstSector(readBuffer);

                volumeMetaData.setPath(image);

                result.add(volumeMetaData);
            }

        } catch (TskCoreException ex) {
            Exceptions.printStackTrace(ex);
        }
        return result;
    }
}
