/*
 * Copyright 2017 Phoboslabs.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.phoboslabs.illuminati.hdfs.vo;

import me.phoboslabs.illuminati.common.util.StringObjectUtils;
import me.phoboslabs.illuminati.hdfs.enums.HDFSSecurityAuthentication;
import me.phoboslabs.illuminati.hdfs.enums.HDFSSecurityAuthorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HDFSConnectionInfo {

    private final static Logger HDFS_LOGGER = LoggerFactory.getLogger(HDFSConnectionInfo.class);

    protected String uriAddress;
    protected int port;
    protected String hdfsUser;

    private String homeDir = "/";
    private HDFSSecurityAuthentication hdfsSecurityAuthentication = HDFSSecurityAuthentication.SIMPLE;
    private HDFSSecurityAuthorization hdfsSecurityAuthorization = HDFSSecurityAuthorization.FALSE;
    private int rpcTimeout = 10000;
    private boolean dfsSupportAppend = false;

    private final static String HADOOP_PREFIX = "hdfs://";
    private final static String HADOOP_CENTER_FIX_WITH_PORT = ":";
    private final static String HADOOP_POSTFIX = "/";

    private final static String HDFS_CONNECTION_URI = "hdfs.connection.uri";
    private final static String HDFS_CONNECTION_PORT = "hdfs.connection.port";
    private final static String HDFS_CONNECTION_AUTHENTICATION = "hdfs.connection.authentication";
    private final static String HDFS_CONNECTION_AUTHORIZATION = "hdfs.connection.authorization";
    private final static String HDFS_CONNECTION_USER = "hdfs.connection.user";
    private final static String HDFS_CONNECTION_HOME = "hdfs.connection.home";
    private final static String HDFS_CONNECTION_TIMEOUT = "hdfs.connection.timeout";
    private final static String HDFS_CONNECTION_DFS_SUPPORT_APPEND = "hdfs.connection.dfsSupportAppend";

    public HDFSConnectionInfo(Properties prop) {
        this.uriAddress = prop.getProperty(HDFS_CONNECTION_URI);

        try {
            this.port = Integer.parseInt(prop.getProperty(HDFS_CONNECTION_PORT));
        } catch (Exception ignore) {}

        this.hdfsUser = prop.getProperty(HDFS_CONNECTION_USER);
        this.hdfsSecurityAuthentication = HDFSSecurityAuthentication.valueOf(prop.getProperty(HDFS_CONNECTION_AUTHENTICATION));
        this.hdfsSecurityAuthorization = HDFSSecurityAuthorization.valueOf(prop.getProperty(HDFS_CONNECTION_AUTHORIZATION));
        this.homeDir = prop.getProperty(HDFS_CONNECTION_HOME);

        try {
            this.rpcTimeout = Integer.parseInt(prop.getProperty(HDFS_CONNECTION_TIMEOUT));
        } catch (Exception ignore) {}

        try {
            this.dfsSupportAppend = Boolean.parseBoolean(prop.getProperty(HDFS_CONNECTION_DFS_SUPPORT_APPEND));
        } catch (Exception ignore) {}
    }

    public HDFSConnectionInfo(final String uriAddress, final int port, final String hdfsUser) {
        this.uriAddress = uriAddress;
        this.port = port;
        this.hdfsUser = hdfsUser;
    }

    public boolean isValid () throws Exception {
        if (StringObjectUtils.isNotValid(this.uriAddress)) {
            final String errorMessage = "uriAddress is required value.";
            HDFS_LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        if (this.port == 0) {
            final String errorMessage = "port is required value.";
            HDFS_LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        if (StringObjectUtils.isNotValid(this.hdfsUser)) {
            final String errorMessage = "HDFS User is required value.";
            HDFS_LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        if (StringObjectUtils.isNotValid(this.homeDir)) {
            final String errorMessage = "homeDir is required value.";
            HDFS_LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        if (this.hdfsSecurityAuthentication == null) {
            final String errorMessage = "hadoopSecurityAuthentication is required value.";
            HDFS_LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        if (this.hdfsSecurityAuthorization == null) {
            final String errorMessage = "hadoopSecurityAuthorization is required value.";
            HDFS_LOGGER.error(errorMessage);
            throw new Exception(errorMessage);
        }
        return true;
    }

    public void setHomeDir(final String homeDir) {
        this.homeDir = homeDir;
    }

    public void setRpcTimeout(final int rpcTimeout) {
        this.rpcTimeout = rpcTimeout;
    }

    public String getHDFSUser() {
        return this.hdfsUser;
    }

    public String getHomeDir() {
        return this.homeDir;
    }

    public String getHDFSUriAddress () {
        return new StringBuilder(HADOOP_PREFIX)
                .append(this.uriAddress)
                .append(this.HADOOP_CENTER_FIX_WITH_PORT)
                .append(this.port)
                .append(this.HADOOP_POSTFIX).toString();
    }

    public void setHDFSSecurityAuthentication(String hdfsSecurityAuthentication) {
        this.hdfsSecurityAuthentication = HDFSSecurityAuthentication.valueOf(hdfsSecurityAuthentication);
    }
    public String getHDFSSecurityAuthenticationType() {
        return this.hdfsSecurityAuthentication.getAuthType();
    }

    public void setHDFSSecurityAuthorization(String hdfsSecurityAuthorization) {
        this.hdfsSecurityAuthorization = HDFSSecurityAuthorization.valueOf(hdfsSecurityAuthorization);
    }
    public String getHDFSSecurityAuthorizationValue() {
        return this.hdfsSecurityAuthorization.getIsAuthorization();
    }

    public String getRpcTimeout() {
        return String.valueOf(this.rpcTimeout);
    }

    public String isDfsSupportAppend() {
        return String.valueOf(this.dfsSupportAppend);
    }
}
