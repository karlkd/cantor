FROM openjdk:8-jdk-alpine

ARG SC_HOME="/cater"

RUN mkdir ${SC_HOME} \
    && mkdir /jmeter

COPY ./*.jmx ${SC_HOME}
COPY ./result ${SC_HOME}
COPY ./*.tgz /jmeter
COPY ./run.sh ${SC_HOME}

RUN cd /jmeter/ \
    && tar -xvzf apache-jmeter-4.0.tgz \
    && rm apache-jmeter-4.0.tgz

ENV JMETER_HOME /jmeter/apache-jmeter-4.0/

# Add Jmeter to the Path
ENV PATH $JMETER_HOME/bin:$PATH

WORKDIR ${SC_HOME}

CMD chmod +x run.sh && sh /cater/run.sh