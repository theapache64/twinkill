# TwinKill

Utility package wrapped around android JetPack components

### Installation

```groovy
implementation 'com.theapache64.twinkill:twinkill:2.1.2-alpha04'
```

### Usage

 - Basic Initialization
 
 Init `TwinKill` in your `Application` class
 
 ```kotlin
 class App : Application() {
     override fun onCreate() {
         super.onCreate()
         
         TwinKill.init(
             TwinKill.builder()
                 .setDefaultFont(Font.GoogleSansRegular)
                 .build()
         )
     }
 }
```



### Components

   
 - Fonts
 
    With `TwinKill`, you'll get `Google-Sans` with it's 3 variants, ie Regular, Medium and Bold.
    You can set the default font while initializing the `TwinKill`
    
    ```kotlin
       TwinKill.builder()
                    .setDefaultFont(Font.GoogleSansRegular)
                    .build() 
    ```
 - Encryption
   
   With `TwinKill`, encrypting and decrypting strings are made easy. Use the `DarkKnight` class to do the same.   
   ```kotlin
   val encString = DarkKnight.getEncrypted("Hello TwinKill")
   val decString = DarkKnight.getDecrypted(encString)
   println(decString) // Hello TwinKill
   ```

    
### Sub-Components

Components that are dependant on another components
    
 - OkHttpInterceptors
    
    - CurlInterceptor
      
        `TwinKill` comes with a `CurlInterceptor` for `OkHttp`. By default, it's not attached.
        You can add the interceptor and many other using the `TwinKill` builder.
        
        
        ```kotin
        TwinKill.builder()
                        .addOkHttpInterceptor(CurlInterceptor())
                        .build()
        ```
    
    - AuthorizationInterceptor
    
        - coming soon
    
    
 - Dagger Modules
 
    - BaseNetworkModule
        
        1. HttpLoggingInterceptor
        2. OkHttpClient
        3. Retrofit    
       
        - coming soon
        
        
 - Room
 
    - DateConverter
     
        Converter for Room database, to parse `Long` from `Date` and vice-versa
   
 
 
### Features

 - coming soon

### Usage

 - coming soon