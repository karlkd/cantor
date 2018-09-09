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
  
Service RESTful API:

| API | HTTP Method | Parameters | Request example | Return |
|:--- |:----------- |:---------- |:--------------- |:------ |
| `/id` | GET | *cate*: Custom category, *range*: How many IDs do you want return in a batch | `http://localhost:8080/id?cate=0&range=100` | `{"start": "18446744073709551616", "range":"100"}` |
| `/info` | GET | *id*: The ID to decode | `http://127.0.0.1:8080/info?id=36313111556915201` | `{"sequence": 1,"descriptor": 1,"category": 2,"timestamp": 21664133}` |

## SDK

A Java SDK to improve the productivity of interacting with Cantor service.
- Design for thread safety
- Cache the IDs in memory with a TTL. When IDs are expired or out of stock, SDK will retrieve a new batch from Cantor service.

## Monitoring
Monitoring by [Jmxtrans](https://github.com/jmxtrans/jmxtrans) & [influxdb](https://github.com/influxdata/influxdb) & [grafana](https://github.com/grafana/grafana)

![Monitoring](https://raw.githubusercontent.com/git-hacker/cantor/master/images/monitoring.png)



## Performance Test
Uses apache-jmeter as performance test client. Jmeter version: `apache-jmeter-4.0`.
  
- Script
  - cantor_sc_100.jmx
  - cantor_sc_200.jmx
  - cantor_sc_400.jmx
  - cantor_sc_1000.jmx
  - cantor_sc_5000.jmx
  - cantor_sc_5000_10.jmx
  - cantor_sc_10000.jmx
  - cantor_sc_10000_10.jmx
  - cantor_sc_10000_300.jmx
  
- Start Jmeter 
  - `docker exec -it <CONTAINER> /jmeter/apache-jmeter-4.0/bin/jmeter -n -t <Script>`

- Jmeter result
  - `docker exec -it <CONTAINER> sort r.csv | uniq -c | awk '{print $1}'|sort|uniq -c`
