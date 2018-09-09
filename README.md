Cantor
===

## Introduction

## User guide

## Service

## Persistence
- Redis
- HBase

## SDK

## Monitoring
Used [Jmxtrans](https://github.com/jmxtrans/jmxtrans) & [influxdb](https://github.com/influxdata/influxdb) & [grafana](https://github.com/grafana/grafana)

![Monitoring](https://raw.githubusercontent.com/git-hacker/cantor/master/images/monitoring.png)



## Performance Test
- Jmeter
  - sort r.csv | uniq -c | awk '{print $1}'|sort|uniq -c
