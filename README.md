# LinkMetadata
Get OpenGraph data (a.k.a link metadata) from a website

## Setup
Add Jitpack in project build.gradle
```Java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  
Add library in your app module (build.gradle)
  ```Java
  dependencies {
	        implementation 'com.github.maddog05:LinkMetadata:1.0'
	}
 ```
 
 ## How to use
 ```Kotlin
 LinkMetadata("http://www.helloworld.com")
            .execute(object : LinkMetadata.OnMetadataResultListener {
                override fun onError(text: String) {

                }

                override fun onSuccess(result: LinkMetadata.Result) {
                    result.title
                    result.description
                    result.url
                    result.image
                }

            })
 ```
