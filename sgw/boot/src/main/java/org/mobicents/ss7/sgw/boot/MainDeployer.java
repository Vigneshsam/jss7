/*
 * JBoss, Home of Professional Open Source
 * Copyright XXXX, Red Hat Middleware LLC, and individual contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.ss7.sgw.boot;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jboss.kernel.Kernel;
import org.jboss.kernel.plugins.deployment.xml.BasicXMLDeployer;
import org.jboss.kernel.spi.deployment.KernelDeployment;
import org.mobicents.ss7.sgw.Version;

/**
 * Simplified deployement framework designed for hot deployement of endpoints
 * and media components.
 * 
 * Deployement is represented by tree of folders. Each folder may contains one
 * or more deployement descriptors. The most top deployment directory is
 * referenced as root. Maindeployer creates recursively HDScanner for root and
 * each nested directoty. The HDScanner corresponding to the root directory is
 * triggered periodicaly by local timer and in it order starts nested scanners
 * recursively.
 * 
 * @author kulikov
 * @author amit bhayani
 */
public class MainDeployer {

    /** JBoss microconatiner kernel */
    private Kernel kernel;
    /** Basic deployer */
    private BasicXMLDeployer kernelDeployer;
    private KernelDeployment deployment;

    /** Filter for selecting descriptors */
    private FileFilter fileFilter;
    /** Root deployment directory as string */
    private String path;
    /** Root deployment directory as file object */
    private File root;
    /** Logger instance */
    private Logger logger = Logger.getLogger(MainDeployer.class);

    /**
     * Creates new instance of deployer.
     */
    public MainDeployer() {
    }

    /**
     * Gets the path to the to the root deployment directory.
     * 
     * @return path to deployment directory.
     */
    public String getPath() {
        return path;
    }

    /**
     * Modify the path to the root deployment directory
     * 
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
        root = new File(path);
    }

    /**
     * Gets the filter used by Deployer to select files for deployement.
     * 
     * @return the file filter object.
     */
    public FileFilter getFileFilter() {
        return fileFilter;
    }

    /**
     * Assigns file filter used for selection files for deploy.
     * 
     * @param fileFilter
     *            the file filter object.
     */
    public void setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }

    /**
     * Starts main deployer.
     * 
     * @param kernel
     *            the jboss microntainer kernel instance.
     * @param kernelDeployer
     *            the jboss basic deployer.
     */
    public void start(Kernel kernel, BasicXMLDeployer kernelDeployer) throws Throwable {
        Version version = Version.instance;
        this.kernel = kernel;
        this.kernelDeployer = kernelDeployer;

        try {
            Configuration d = new Configuration("root", root);
            URL url = d.getConfig();
            deployment = kernelDeployer.deploy(url);
            kernelDeployer.validate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("[[[[[[[[[ " + version.toString() + " Started " + "]]]]]]]]]");
    }

    /**
     * Shuts down deployer.
     */
    public void stop() {
        kernelDeployer.undeploy(deployment);
        logger.info("Stopped");
    }

}
