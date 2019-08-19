#!/bin/bash

# Before run Spark on Angel application, you must follow the steps:
# 1. confirm Hadoop and Spark have ready in your environment
# 2. unzip sona-<version>-bin.zip to local directory
# 3. upload sona-<version>-bin directory to HDFS
# 4. set the following variables, SPARK_HOME, SONA_HOME, SONA_HDFS_HOME, SONA_VERSION, ANGEL_VERSION, ANGEL_UTILS_VERSION


export JAVA_HOME=
export HADOOP_HOME=
export SPARK_HOME=
export SONA_HOME=
export SONA_HDFS_HOME=
export SONA_VERSION=0.1.0-SNAPSHOT
export ANGEL_VERSION=3.0.0-SNAPSHOT
export ANGEL_UTILS_VERSION=0.1.0-SNAPSHOT


scala_jar=scala-library-2.11.8.jar
angel_ps_external_jar=fastutil-7.1.0.jar,htrace-core-2.05.jar,sizeof-0.3.0.jar,kryo-shaded-4.0.0.jar,minlog-1.3.0.jar,memory-0.8.1.jar,commons-pool-1.6.jar,netty-all-4.1.17.Final.jar,hll-1.6.0.jar
angel_ps_jar=angel-format-${ANGEL_UTILS_VERSION}.jar,angel-mlcore-${ANGEL_UTILS_VERSION}.jar,angel-ps-core-${ANGEL_VERSION}.jar,angel-ps-mllib-${ANGEL_VERSION}.jar,angel-ps-psf-${ANGEL_VERSION}.jar,angel-math-${ANGEL_UTILS_VERSION}.jar,angel-ps-graph-${ANGEL_VERSION}.jar

sona_jar=core-${SONA_VERSION}.jar,angelml-${SONA_VERSION}.jar
sona_external_jar=fastutil-7.1.0.jar,htrace-core-2.05.jar,sizeof-0.3.0.jar,kryo-shaded-4.0.0.jar,minlog-1.3.0.jar,memory-0.8.1.jar,commons-pool-1.6.jar,netty-all-4.1.17.Final.jar,hll-1.6.0.jar,jniloader-1.1.jar,native_system-java-1.1.jar,arpack_combined_all-0.1.jar,core-1.1.2.jar,netlib-native_ref-linux-armhf-1.1-natives.jar,netlib-native_ref-linux-i686-1.1-natives.jar,netlib-native_ref-linux-x86_64-1.1-natives.jar,netlib-native_system-linux-armhf-1.1-natives.jar,netlib-native_system-linux-i686-1.1-natives.jar,netlib-native_system-linux-x86_64-1.1-natives.jar,jettison-1.4.0.jar,json4s-native_2.11-3.2.11.jar

dist_jar=${angel_ps_external_jar},${angel_ps_jar},${scala_jar},${sona_jar}
local_jar=${sona_external_jar},${angel_ps_jar},${sona_jar}

for f in `echo $dist_jar | awk -F, '{for(i=1; i<=NF; i++){ print $i}}'`; do
	jar=${SONA_HDFS_HOME}/lib/${f}
    if [ "$SONA_ANGEL_JARS" ]; then
        SONA_ANGEL_JARS=$SONA_ANGEL_JARS,$jar
    else
        SONA_ANGEL_JARS=$jar
    fi
done
echo SONA_ANGEL_JARS: $SONA_ANGEL_JARS
export SONA_ANGEL_JARS 


for f in `echo $local_jar | awk -F, '{for(i=1; i<=NF; i++){ print $i}}'`; do
	jar=${SONA_HDFS_HOME}/lib/${f}
    if [ "$SONA_SPARK_JARS" ]; then
        SONA_SPARK_JARS=$SONA_SPARK_JARS,$jar
    else
        SONA_SPARK_JARS=$jar
    fi
done
echo SONA_SPARK_JARS: $SONA_SPARK_JARS
export SONA_SPARK_JARS