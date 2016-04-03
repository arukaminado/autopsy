/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.autopsy.casemodule;

/**
 *
 * @author root
 */
class VolumeMetaData {

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
}
