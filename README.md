Cantor
===

## Introduction
Cantor is a global sequence generator service. 
Cantor can generate unique, relatively orderly, inverse decodable, 64-bit integer IDs.

Protocol of ID

| 1 bit | 2 bit | 2 bit | 8 bit | 2 bit | 28 bit | 17 bit |
|:----  |:----  |:----  |:----  |:----  |:------ |:------ |
| Sign bit, never used | Protocol version | Generation sources descriptor | Custom spaces | Cantor service instance number | Timestamp | Sequence |

In short, Cantor service guarantees that all unique IDs are generated based on its logic clock and a persistent sequence consuming state.
When persistence service is down, Cantor service can downgrade to generate ID in local. 
 
## User guide

All components are dockerized. Deployment demonstration:
- Build Java project
  - `$ cd $PROJECT_ROOT`
  - `$ mvn clean install -N && cd http && mvn clean install && cd ../service && mvn clean package` 
- Build Docker images and deploy to docker swarm:
  - `$ cd $PROJECT_ROOT`
  - `$ docker-compose build`
  - `$ docker stack deploy -c docker-compose.yml cantor`

## Service

## Persistence
- Redis
- HBase

## SDK

## Monitoring
Monitoring by [Jmxtrans](https://github.com/jmxtrans/jmxtrans) & [influxdb](https://github.com/influxdata/influxdb) & [grafana](https://github.com/grafana/grafana)

![Monitoring](https://raw.githubusercontent.com/git-hacker/cantor/master/images/monitoring.png)



## Performance Test
- Jmeter
  - sort r.csv | uniq -c | awk '{print $1}'|sort|uniq -c
