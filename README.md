### 使用方式：
#### *於 Android Studio 的 build.gradle (Project) 加入*


```
 allprojects {
    repositories {
        
       maven { url 'https://jitpack.io' }
    }
 }
```


#### *於 Android Studio 的 build.gradle **(Module)** 加入*


```
dependencies {

    implementation 'com.github.GISFCU-TM:DataTransfer:0.0.1'
}
```
---

### 呼叫方式：(同步完成之後，才能正常使用。)