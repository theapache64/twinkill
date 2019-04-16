# Logger
Logging made simple

### Installation

```groovy
implementation 'com.theapache64.twinkill:logger:0.0.1-alpha01'
```


### Usage

In Android, to log into `Logcat`, we use the `Log` class. 
The problem is, If we want to print a value to the logcat, we'll need
to attach a tag variable, which in most case, the name of the class.

```kotlin
val TAG = MyClass.javaClass.simpleName

Log.d(TAG,"Data is $data")
```

This is annoying and very time consuming.

With the help of `Logger`, the above code can be replaced with

```kotlin
debug("Data is $data")
```

`Logger` will automatically create the tag name for you.

| Default  | Logger |
|----------|--------|
|Log.d(TAG,"Data is $data")|debug("Data is $data")|
|Log.e(TAG,"Data is $data")|mistake("Data is $data")|
|Log.w(TAG,"Data is $data")|warning("Data is $data")|
|Log.i(TAG,"Data is $data")|info("Data is $data")|
|Log.v(TAG,"Data is $data")|verbose("Data is $data")|
|Log.wtf(TAG,"Data is $data")|wtf("Data is $data")|

### Author
theapache64
