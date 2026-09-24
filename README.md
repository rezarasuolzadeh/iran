[![](https://jitpack.io/v/rezarasuolzadeh/iran.svg)](https://jitpack.io/#rezarasuolzadeh/iran)
![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

### کتابخونه‌ی اندرویدی نقشه‌ی ایران
این کتابخونه رو برای این توسعه دادم که استفاده از اِلِمان های نقشه ایران تا جزئی ترین بخش ممکن، با یک UI خوب و راحت برای کاربر توی اپلیکیشن های اندروید فراهم بشه. تا جایی که ممکن بوده برام اطلاعات نقشه رو بر حسب اطلاعاتی که وجود داشته توی سایت های مختلف، پیاده سازی کردم و برخی موارد هم اصلاح شدن تا قابل اتکا و درست باشن برای استفاده برنامه نویسا. این کتابخونه رو با Compose پیاده‌سازی کردم که هم به روز تر باشه و هم استفاده ازش راحت تر. اگه خواستین یه نگاه کلی به ویژگی های کتابخونه بندازین بدون اینکه به پروژتون اضافش کنین، میتونین از همینجا اپلیکیشن دمو رو که با استفاده از همین کتابخونه ساختمش، دانلود و بررسیش کنین:
<br>&nbsp;<br>
<p align="center">
    <a href="https://github.com/rezarasuolzadeh/iran/releases/download/1.0.0/iran.1.0.0.apk">
        <img alt="Github" src="/images/github.png" width="180" height="55">
    </a>
</p>

<br>&nbsp;<br>

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
## تابع نقشه‌ی ایران 
<p align="center">
    <img alt="Iran Map" src="/images/iran_map.png"  width="400" height="400"> 
</p>

با فراخوانی تابع زیر، شما میتونید خروجی بالا رو تو هر جایی از ui پروژه‌ی compose تون که بخواین داشته باشین:

```kotlin
IranMap(
    modifier = Modifier,
    selectedProvinceId = "Isfahan",
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
پنجمن پارامتر waterColor هست که رنگ دریاچه ها رو تعیین میکنه (البته فعلا این رنگ رو صرفا میتونین رنگ دریاچه ارومیه لحاظ کنین). توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapWaterColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapWaterColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
waterColor = Color(0xFF90CAF9)
```
ششمین پارامتر borderColor هست که رنگ خطوط جدا کننده استان ها (یا همون مرز استان ها) رو تعیین میکنه. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapInnerBorderColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapInnerBorderColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
borderColor = Color(0xFF37474F)
```
هفتمین پارامتر onProvinceIdSelected هست که ID استانی که توسط کاربر انتخاب شده رو برمیگردونه به شما. ID برگشتی هم از جنس ?String هستش که میتونه null هم باشه.
```kotlin
onProvinceIdSelected = { id ->
    // you have access to the selected province ID here
}
```
هشتمین پارامتر onProvinceNameSelected هست که نام فارسی استانی که توسط کاربر انتخاب شده رو برمیگردونه به شما. نام برگشتی هم از جنس ?String هستش که میتونه null هم باشه.
```kotlin
onProvinceNameSelected = { name ->
    // you have access to the selected province Name here
}
```
نهمین پارامتر onProvinceInfoSelected هست که اطلاعات جغرافیایی استانی که توسط کاربر انتخاب شده رو برمیگردونه به شما. اطلاعات برگشتی هم از جنس ?ProvinceInfoModel هستش که میتونه null هم باشه.
```kotlin
onProvinceInfoSelected = { info ->
    // you have access to the selected province Info here
}
```
<br>&nbsp;<br>
<br>&nbsp;<br>
## تابع نقشه‌ی استان 
<p align="center">
    <img alt="Iran Map" src="/images/province_map.png"  width="400" height="400"> 
</p>

با فراخوانی تابع زیر، شما میتونید خروجی بالا رو تو هر جایی از ui پروژه‌‌ی compose تون که بخواین داشته باشین:

```kotlin
ProvinceMap(
    modifier = Modifier,
    provinceId = "Hormozgan",
    selectedCountyId = "Hormozgan_5",
    defaultColor = MapDefaultColor,
    selectedColor = MapSelectedColor,
    innerBorderColor = MapInnerBorderColor,
    outerBorderColor = MapOuterBorderColor,
    onCountyIdSelected = {},
    onCountyNameSelected = {},
    onCountyInfoSelected = {}
)
```
#### پارامترها
اولین پارامتر modifier هست که شما با استفاده از اون میتونید تغییراتی که نیازه توی فاصله، چینش توی صفحه، اندازه و ... روی View انجام بدین. اما مهمترین چیزی که میتونید اینجا کنترلش کنید، اندازه نقشه هست که بسته به نوع UI اپلیکیشن خودتون، نقشه رو بزرگ یا کوچیک کنید که مثالش رو هم طیق کد پایین میتونید ببینین. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
modifier = Modifier.size(300.dp)
```
دومین پارامتر provinceId هست که همون ID استانی هست که میخواین اون رو به کاربر نمایش بدین. این ID رو یا میتونین از تابع قبلی (IranMap) دریافت کنین یا این که با استفاده از توابعی که توی ادامه‌ی این مستند توضیح میدم به دست بیارین. این پارامتر حتما باید مقداردهی باشه وگرنه هیچ استانی به شما نمایش داده نمیشه! 
```kotlin
provinceId = "Hormozgan"
```
سومین پارامتر selectedCountyId هست که همون ID شهرستان انتخاب شده توسط کاربر هست. این مقدار بعد از انتخاب شهرستان با لمس محدوده هر شهرستان در قسمت onCountyIdSelected قابل دریافت هستش یا با استفاده از توابعی که در انتهای این مستند گفته میشه میتونید به دستش بیارین. اگه این پارامتر مقداردهی نشه هیچ شهرستانی به صورت انتخاب شده نمایش داده نمیشه و با لمس هر شهرستان هم هیچ اتفاقی توی نقشه نمیوفته! 
```kotlin
selectedCountyId = "Hormozgan_5"
```
چهارمین پارامتر defaultColor هست که در واقع رنگ پیشفرض شهرستان هاست که تو حالت انتخاب شده قرار ندارن. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapDefaultColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapDefaultColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
defaultColor = Color(0xFFB0BEC5)
```
پنجمین پارامتر selectedColor هست که رنگ شهرستان انتخاب شده رو تعیین میکنه. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapSelectedColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapSelectedColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
selectedColor = Color(0xFF1E88E5)
```
ششمین پارامتر waterColor هست که رنگ دریاچه ها رو تعیین میکنه (البته فعلا این رنگ رو صرفا میتونین رنگ دریاچه ارومیه لحاظ کنین). توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapWaterColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapWaterColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
waterColor = Color(0xFF90CAF9)
```
هفتمین پارامتر innerBorderColor هست که رنگ خطوط جدا کننده شهرستان ها (یا همون مرز شهرستان ها) رو تعیین میکنه. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapInnerBorderColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapInnerBorderColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
innerBorderColor = Color(0xFF37474F)
```
هشتمین پارامتر outerBorderColor هست که رنگ مرز استان (خط دور نقشه استان فعلی) رو تعیین میکنه. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapOuterBorderColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapOuterBorderColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
outerBorderColor = Color(0xFF212121)
```
نهمین پارامتر onCountyIdSelected هست که ID شهرستانی که توسط کاربر انتخاب شده رو برمیگردونه به شما. ID برگشتی هم از جنس ?String هستش که میتونه null هم باشه.
```kotlin
onCountyIdSelected = { id ->
    // you have access to the selected county ID here
}
```
دهمین پارامتر onCountyNameSelected هست که نام فارسی شهرستانی که توسط کاربر انتخاب شده رو برمیگردونه به شما. نام برگشتی هم از جنس ?String هستش که میتونه null هم باشه.
```kotlin
onCountyNameSelected = { name ->
    // you have access to the selected county Name here
}
```
یازدهمین پارامتر onCountyInfoSelected هست که اطلاعات جغرافیایی شهرستانی که توسط کاربر انتخاب شده رو برمیگردونه به شما. اطلاعات برگشتی هم از جنس ?CountyInfoModel هستش که میتونه null هم باشه.
```kotlin
onCountyInfoSelected = { info ->
    // you have access to the selected county Info here
}
```
<br>&nbsp;<br>
<br>&nbsp;<br>
## تابع نقشه‌ی شهرستان 
<p align="center">
    <img alt="Iran Map" src="/images/county_map.png"  width="400" height="400"> 
</p>

با فراخوانی تابع زیر، شما میتونید خروجی بالا رو تو هر جایی از ui پروژه‌‌ی compose تون که بخواین داشته باشین:

```kotlin
CountyMap(
    modifier = Modifier,
    provinceId = "KohgiluyehVaBoyerahmad",
    countyId = "KohgiluyehVaBoyerahmad_8",
    defaultColor = MapDefaultColor,
    borderColor = MapOuterBorderColor
)
```
#### پارامترها
اولین پارامتر modifier هست که شما با استفاده از اون میتونید تغییراتی که نیازه توی فاصله، چینش توی صفحه، اندازه و ... روی View انجام بدین. اما مهمترین چیزی که میتونید اینجا کنترلش کنید، اندازه نقشه هست که بسته به نوع UI اپلیکیشن خودتون، نقشه رو بزرگ یا کوچیک کنید که مثالش رو هم طیق کد پایین میتونید ببینین. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
modifier = Modifier.size(250.dp)
```
دومین پارامتر provinceId هست که همون ID استانی هست که شهرستان انتخابی شما توش قرار داره. این ID رو یا میتونین از تابع قبلی (IranMap) دریافت کنین یا این که با استفاده از توابعی که توی ادامه‌ی این مستند توضیح میدم به دست بیارین. این پارامتر حتما باید مقداردهی باشه وگرنه هیچ استانی به شما نمایش داده نمیشه! 
```kotlin
provinceId = "KohgiluyehVaBoyerahmad"
```
سومین پارامتر countyId هست که همون ID شهرستانی هست که میخواین اون رو به کاربر نمایش بدین. این ID رو یا میتونین از تابع قبلی (ProvinceMap) دریافت کنین یا این که با استفاده از توابعی که توی ادامه‌ی این مستند توضیح میدم به دست بیارین. این پارامتر حتما باید مقداردهی باشه وگرنه هیچ استانی به شما نمایش داده نمیشه! 
```kotlin
countyId = "KohgiluyehVaBoyerahmad_8"
```
چهارمین پارامتر defaultColor هست که در واقع رنگ شهرستان فعلی رو مشخص میکنه. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapSelectedColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapSelectedColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
defaultColor = Color(0xFFB0BEC5)
```
پنجمین پارامتر borderColor هست که رنگ مرز شهرستان (خط دور نقشه شهرستان فعلی) رو تعیین میکنه. توی کتابخونه یه رنگی براش در نظر گرفته شده به نام MapOuterBorderColor که میتونید ازش استفاده کنید یا رنگ مورد نظر خودتون رو بهش بدین. اگر مقدار دهی هم نشه، همون MapOuterBorderColor رو به صورت پیشفرض در نظر میگیره. (این پارامتر اجباری نیست و میتونید مقداردهی نکنید)
```kotlin
borderColor = Color(0xFF212121)
```
<br>&nbsp;<br>
<br>&nbsp;<br>
## توابع کمکی

یه سری توابع هم هستن که به صورت کمکی عمل میکنن و اطلاعاتی که ممکنه شما بهش نیاز داشته باشین، حتی اگه نخواین از نقشه گرافیکی استفاده کنین، رو در اختیارتون میزارن. این توابع که توی توضیحات بالا هم دربارش صحبت کردم با توجه به عملکردشون به صورت زیر دسته بندی شدن:


#### نام استان
ورودی -> ID استان (String)

خروجی -> نام فارسی استان (?String)
```kotlin
getProvinceName(provinceId = "Qazvin")
```


#### اطلاعات استان
ورودی -> ID استان (String)

خروجی -> اطلاعات استان (?ProvinceInfoModel)
```kotlin
getProvinceInfo(provinceId = "SistanVaBaluchestan")
```


#### اطلاعات همه استان ها
ورودی -> نداره

خروجی -> لیست اطلاعات همه استان ها (<List<ProvinceInfoModel) 
```kotlin
getAllProvincessInfo()
```

#### اطلاعات شهرستان های استان
ورودی -> ID استان (String)

خروجی -> لیست اطلاعات شهرستان های استان (<List<CountyInfoModel)
```kotlin
getCounties(provinceId = "Fars")
```


#### تعداد شهرستان های استان
ورودی -> ID استان (String)

خروجی -> تعداد شهرستان های استان (Int)
```kotlin
getNumberOfCounties(provinceId = "Khuzestan")
```


#### نام شهرستان
ورودی -> ID شهرستان (String)

خروجی -> نام فارسی شهرستان (?String)
```kotlin
getCountyName(countyId = "Semnan_3")
```


#### نام مرکز شهرستان
ورودی -> ID شهرستان (String)

خروجی -> نام فارسی مرکز شهرستان (?String)
```kotlin
getCountySeatName(countyId = "Ilam_8")
```


#### اطلاعات شهرستان
ورودی -> ID شهرستان (String)

خروجی -> اطلاعات شهرستان (?CountyInfoModel)
```kotlin
getCountyInfo(countyId = "Yazd_1")
```


#### اطلاعات همه شهرستان ها
ورودی -> نداره

خروجی -> لیست اطلاعات همه شهرستان های کشور (<List<CountyInfoModel)
```kotlin
getAllCountiesInfo()
```
<br>&nbsp;<br>


<div align="center">
ساخته شده با ❤️ برای جامعه‌ی توسعه‌ دهندگان اندروید ایران
</div>
