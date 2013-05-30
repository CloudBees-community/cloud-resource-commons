package org.cloudbees.cloud_resource.model;

import javax.persistence.MappedSuperclass;

/**
 * @author Vivek Pandey
 */
@MappedSuperclass
public class AbstractBindingEdgeModel extends AbstractModel{
    private String source;
    private String sink;
    private String label;
    private String settings;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSink() {
        return sink;
    }

    public void setSink(String sink) {
        this.sink = sink;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

}
