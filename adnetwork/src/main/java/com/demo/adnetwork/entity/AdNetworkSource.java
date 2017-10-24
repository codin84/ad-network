package com.demo.adnetwork.entity;

import javax.persistence.*;

@Entity
@Table(name = "ad_network_source")
public class AdNetworkSource
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String dateParamFormat;

    public AdNetworkSource()
    {
    }

    public AdNetworkSource(Long id, String name, String url, String dateParamFormat)
    {
        this.id = id;
        this.name = name;
        this.url = url;
        this.dateParamFormat = dateParamFormat;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }

    public String getDateParamFormat()
    {
        return dateParamFormat;
    }
}
