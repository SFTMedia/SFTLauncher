package com.blalp.sftwrapper.interfaces;

/**
 * IPath
 */
public interface IPath {
    public String getPathRoot();
    public String getPathInstanceCache();
    public String getFileMultiMCBinary();
    public String getURLMultiMC();
    public String getPathMultiMC(); // MUST end in MultiMC
    public void mkdirs();
	public String getMultiMCArchiveFormat();
	public String getPathMultiMCExtract();
}