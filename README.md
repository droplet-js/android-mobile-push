# android-mobile-push

### 百度移动推送
````
# 通知打开APP
open_type = 2
pkg_content = xxx（非Intent.toURI的任意非空字符串）
````

### 阿里云推送
````
# 通知打开APP
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
    implementation "io.github.v7lin:mobile-push-baidu-android:${latestVersion}"
    ...
}
...
````
