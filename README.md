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
#### پارامترها
اولین پارامتر modifier هست که شما با استفاده از اون میتونید تغییراتی که نیازه توی فاصله، چینش توی صفحه، اندازه و ... روی View انجام بدین. اما مهمترین چیزی که میتونید اینجا کنترلش کنید، اندازه نقشه هست که بسته به نوع UI اپلیکیشن خودتون، نقشه رو بزرگ یا کوچیک کنید که مثالش رو هم طیق کد پایین میتونید ببینین. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)

```kotlin
modifier = Modifier.size(400.dp)
```
دومین پارامتر selectedProvinceId هست که همون ID استان انتخاب شده توسط کاربر هست. این مقدار بعد از انتخاب استان با لمس محدوده هر استان در قسمت onProvinceIdSelected قابل دریافت هستش یا با استفاده از توابعی که در انتهای این مستند گفته میشه میتونید به دستش بیارین. اگه این پارامتر مقداردهی نشه هیچ استانی به صورت انتخاب شده نمایش داده نمیشه و با لمس هر استان هم هیچ اتفاقی توی نقشه نمیوفته! 
```kotlin
selectedProvinceId = "Isfahan"
```
سومین پارامتر defaultColor هست که در واقع رنگ پیشفرض استان هاست که تو حالت انتخاب شده قرار ندارن. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapDefaultColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapDefaultColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
defaultColor = Color(0xFFB0BEC5)
```

چهارمین پارامتر selectedColor هست که رنگ استان انتخاب شده رو تعیین میکنه. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapSelectedColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapSelectedColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
selectedColor = Color(0xFF1E88E5)
```
<br>&nbsp;<br>
