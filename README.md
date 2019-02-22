# android-mobile-push

[![Build Status](https://cloud.drone.io/api/badges/v7lin/android-mobile-push/status.svg)](https://cloud.drone.io/v7lin/android-mobile-push)
[ ![Download](https://api.bintray.com/packages/v7lin/maven/mobile-push-aliyun-android/images/download.svg) ](https://bintray.com/v7lin/maven/mobile-push-aliyun-android/_latestVersion)
[ ![Download](https://api.bintray.com/packages/v7lin/maven/mobile-push-baidu-android/images/download.svg) ](https://bintray.com/v7lin/maven/mobile-push-baidu-android/_latestVersion)

### 百度移动推送
````
# 通知打开APP
open_type = 2
pkg_content = xxx（非Intent.toURI的任意非空字符串）
````

### 阿里云推送
````
# 通知打开APP
参考官网
````

### snapshot

````
ext {
    latestVersion = '1.0.0-SNAPSHOT'
}

allprojects {
    repositories {
        ...
        // aliyun
        maven {
            url 'http://maven.aliyun.com/nexus/content/repositories/releases/'
        }

        maven {
            url 'https://oss.jfrog.org/artifactory/oss-snapshot-local'
        }
        ...
    }
}
````

### release

````
ext {
    latestVersion = '1.0.0'
}

allprojects {
    repositories {
        ...
        jcenter()

        // aliyun
        maven {
            url 'http://maven.aliyun.com/nexus/content/repositories/releases/'
        }
        ...
    }
}
````

### usage

android
````
...
dependencies {
    ...
//    implementation "io.github.v7lin:mobile-push-aliyun-android:${latestVersion}"
    implementation "io.github.v7lin:mobile-push-baidu-android:${latestVersion}"
    ...
}
...
````
