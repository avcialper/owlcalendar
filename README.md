# OwlCalendar

[![](https://jitpack.io/v/avcialper/owlcalendar.svg)](https://jitpack.io/#avcialper/owlcalendar)

![banner](./assets/owl.png)

**OwlCalendar** is a calendar library developed for **Android** applications. It contains a
ready-to-use calendar component. Users can add **marked days** and a **line date** to the calendar.
When adding marked days, colors can also be specified. The calendar can operate in three different
modes:

- **Normal Mode** <br>
  If the user does not specify a mode, this mode is selected by default. In this mode, marked days
  and line date can be added to the calendar. The user can navigate through the regular calendar.
- **Single Selectable Mode** <br>
  This mode is used to select a single date from the calendar. Marked days and line date cannot be
  added in this mode. However, a start date can be set in this mode.
- **Range Selectable Mode** <br>
  This mode allows the user to select a specific date or a date range. Marked days cannot be added,
  but line date and start date can be added. If a line date is set, the start date cannot be set
  again. Only one of these two values can be set at a time. Depending on the needs, the user can
  either provide line date data (start and end date) or a single day information.

# Installation

First, add the following dependency to settings.gradle.kts file

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Later, add this implementation build.gradle.kts (Module:app)

```gradle
dependencies {
    ..
    implementation("com.github.avcialper:owlcalendar:${latest_version}")
}
```

TOML usage

```toml
[versions]
owlcalendar = "latest_version"

[libraries]
owlcalendar = { module = "com.github.avcialper:owlcalendar", version.ref = "owlcalendar" }
```

```gradle
dependencies {
    ..
    implementation(libs.owlcalendar)
}
```

# Usage

Add these codes to the used layout

```xml

<com.avcialper.owlcalendar.OwlCalendar android:id="@+id/owlCalendar"
    android:layout_width="match_parent" android:layout_height="wrap_content" />

```

[![Download APK](./assets/download-apk.png)](./sample/apk/)

# Parameters

| Name             | Description                        |
|------------------|------------------------------------|
| calendarMode     | Get and update calendar mode.      |
| pickerButtonText | Get and update picker button text. |
| startYear        | Get current start year.            |
| startMonth       | Get current start month.           |
| startDate        | Get start date.                    |

# Functions

| Name                        | Description                                                                                   |
|-----------------------------|-----------------------------------------------------------------------------------------------|
| addMarkedDay                | Adds a new marked day to the calendar.                                                        |
| addMarkedDays               | Adds a list of marked days to the calendar.                                                   |
| setLineDate                 | Sets the calendar's line date and updates the start date if range selection mode is enabled.  |
| setStartDate                | Sets the start date of the calendar using the given year and month.                           |
| setStartDate                | Sets the start date with a specific day if the mode allows selection and no line date is set. |
| setOnDayClickListener       | Sets a listener that triggers when a day is clicked.                                          |
| setOnLineDateChangeListener | Sets a listener that triggers when a range selection is completed.                            |
| restore                     | Restores the calendar state when multiple calendar instances are used.                        |
| changeLocale                | Changes the calendar's locale.                                                                |

> [!WARNING]  
> If you have multiple calendars in your application, use the **restore** function in the **onResume
** method. This function will restore all data and attributes for the current calendar instance.

# Attributes

| Name                           | Description                                                              |
|--------------------------------|--------------------------------------------------------------------------|
| selected_date_background_color | Background color for the selected date.                                  |
| today_text_color               | Text color for today's date.                                             |
| day_text_color                 | Text color for all days except today.                                    |
| day_name_text_color            | Text color for day names (e.g., Mon, Tue).                               |
| date_text_color                | Text color for the month and year displayed at the top.                  |
| line_background_color          | Background color for the line date selection.                            |
| picker_button_text_color       | Text color for buttons in the date picker.                               |
| picker_button_text             | Text displayed on the date picker button.                                |
| picker_button_background_color | Background color of the date picker button.                              |
| picker_background_color        | Background color of the date picker.                                     |
| mode                           | Defines the calendar mode (normal, single_selectable, range_selectable). |
