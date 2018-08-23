# ImageTextProgressBar

* `效果图以及详细介绍请移居我的博客查看，欢迎提出问题`

* `[我的博客](https://blog.csdn.net/qq_21434809/article/details/81979460)`

![](https://img-blog.csdn.net/20180823120103720?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNDM0ODA5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)  

How to

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  	}
Step 2. Add the dependency

	dependencies {

		implementation 'com.github.hanxiaoqiangfly:ImageTextProgressBar:v1.0'
	
	}
