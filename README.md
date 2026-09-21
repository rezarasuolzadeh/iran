[![](https://jitpack.io/v/rezarasuolzadeh/iran.svg)](https://jitpack.io/#rezarasuolzadeh/iran)

### درباره کتابخونه
این کتابخونه رو برای این توسعه دادم که استفاده از اِلِمان های نقشه ایران تا جزئی ترین بخش ممکن، با یک UI خوب و راحت برای کاربر توی اپلیکیشن های اندروید فراهم بشه. تا جایی که ممکن بوده برام اطلاعات نقشه رو بر حسب اطلاعاتی که وجود داشته توی سایت های مختلف، پیاده سازی کردم و برخی موارد هم اصلاح شدن تا قابل اتکا و درست باشن برای استفاده برنامه نویسا. این کتابخونه رو با Compose پیاده‌سازی کردم که هم به روز تر باشه و هم استفاده ازش راحت تر.


### نحوه استفاده
نحوه استفاده از کتابخونه هم خیلی سادس، اول وارد فایل setting.gradle.kts میشین و تیکه کد زیر رو تو قسمتی که مشخص شده اضافه میکنید:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```
بعدشم وارد فایل build.gradle.kts مربوط به app میشین و تیکه کد زیر رو هم تو قسمتی که مشخص شده اضافه میکنید:

```kotlin
dependencies {
    implementation("com.github.rezarasuolzadeh:iran:1.0.0")
}
```
<br>&nbsp;<br>
## تابع نقشه ایران 
<p align="center">
    <img alt="Iran Map" src="/images/iran_map.png"  width="400" height="400"> 
</p>

با فراخوانی تابع زیر، شما میتونید خروجی بالا رو (که به صورت Dialog هست) تو هر جایی از کد compose تون که بخواین داشته باشین:

```kotlin
IranMap(
    modifier = Modifier,
    selectedProvinceId = "",
    defaultColor = MapDefaultColor,
    selectedColor = MapSelectedColor,
    waterColor = MapWaterColor,
    borderColor = MapInnerBorderColor,
    onProvinceIdSelected = {},
    onProvinceNameSelected = {},
    onProvinceInfoSelected = {}
)
```
در این قسمت شما میتونید با استفاده از پارامترهای ورودی تابع (که اسمشم جوری گذاشتم که گویا باشه) استفاده کنید و تا جای ممکن دیالوگ رو برای خودتون شخصی سازی کنین. تو اینجا دو تا lambda function داریم که اولیش onCancel هست که وقتی روی دکمه انصراف (یا هر چیزی که اسمش رو خودتون توی cancelTitle بزارید) زده بشه فراخوانی میشه:
```kotlin
onProvinceIdSelected = { id ->
    // obtain the ID of selected province from here
}
```
دومیش هم onTimeSelect هست که وقتی روی دکمه ثبت (یا هر چیزی که اسمش رو خودتون توی confirmTitle بزارید) زده بشه فراخوانی میشه که مقدار selectedTime همون زمانی هست که انتخاب کردید:
```kotlin
onProvinceNameSelected = { name ->
    // obtain the Name of selected province from here
}
```
دومیش هم onTimeSelect هست که وقتی روی دکمه ثبت (یا هر چیزی که اسمش رو خودتون توی confirmTitle بزارید) زده بشه فراخوانی میشه که مقدار selectedTime همون زمانی هست که انتخاب کردید:
```kotlin
onProvinceInfoSelected = { info ->
    // obtain the Information of selected province from here
}
```
<br>&nbsp;<br>