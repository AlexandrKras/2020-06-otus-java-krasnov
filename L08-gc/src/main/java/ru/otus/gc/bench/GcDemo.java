package ru.otus.gc.bench;

import com.sun.management.GarbageCollectionNotificationInfo;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

/*
О формате логов
http://openjdk.java.net/jeps/158


-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/

public class GcDemo {
    private static long countBuildsYoung = 0;
    private static long countBuildsOld = 0;
    private static long timeBuildYoung = 0;
    private static long timeBuildOld = 0;
    private static long durationMaxYoung = 0;
    private static long durationMaxOld = 0;

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        int size = 5 * 1000 * 1000;
        int loopCounter = 1000;
        //int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
//        mbean.run();
        int cycle = mbean.run1Minute();
//        mbean.runOutOfMemory();

        System.out.println("time: " + (System.currentTimeMillis() - beginTime) / 1000);
        System.out.println("cycle: " + cycle);
        System.out.println("Count builds:");
        System.out.println("Young: " + countBuildsYoung);
        System.out.println("Old: " + countBuildsOld);
        System.out.println("Time build:");
        System.out.println("Young: " + timeBuildYoung);
        System.out.println("Old: " + timeBuildOld);
        System.out.println("Average Young: " + timeBuildYoung / countBuildsYoung);
        System.out.println("Average Old: " + timeBuildOld / Math.max(countBuildsOld, 1));
        System.out.println("Max duration Young: " + durationMaxYoung);
        System.out.println("Max duration Old: " + durationMaxOld);
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    long id = info.getGcInfo().getId();

                    if (gcAction.contains("minor")) {
                        countBuildsYoung = id;
                        timeBuildYoung += duration;
                        durationMaxYoung = Math.max(durationMaxYoung,duration);
                    } else {
                        countBuildsOld = id;
                        timeBuildOld += duration;
                        durationMaxOld = Math.max(durationMaxOld,duration);
                    }

                    System.out.println("id: " + id + ", start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, emitter);
        }
    }
}
