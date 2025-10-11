;NSIS Modern User Interface

;--------------------------------
;Include Modern UI

  !include "MUI2.nsh"

;--------------------------------
;General

	;defile
	!define APPNAME "Kaata Zero"
	!define COMPANYNAME "VersionPB"
	!define DESCRIPTION "Kaata Zero is the Best way to pass time"
	# These three must be integers
	!define VERSIONMAJOR 1
	!define VERSIONMINOR 1
	!define VERSIONBUILD 1.0.0.0
	# These will be displayed by the "Click here for support information" link in "Add/Remove Programs"
	# It is possible to use "mailto:" links in here to open the email client
	!define HELPURL "http://www.versionpb.co.in" # "Support Information" link
	!define UPDATEURL "http://www.versionpb.co.in" # "Product Updates" link
	!define ABOUTURL "http://www.versionpb.co.in" # "Publisher" link
	# This is the size (in kB) of all the files copied into "Program Files"
	!define INSTALLSIZE 1342
	!define IMAGEDIR "../images"
	!define PROGDIR "../dist"
	!define OUTDIR "../GeneratedEXE"
	!define EXENAME "KaataZero.jar"
	!define ICONNAME "Icon.ico"
	
	
	;Request application privileges for Windows Vista
  RequestExecutionLevel admin
  
  



  ;Default installation folder
  InstallDir "$PROGRAMFILES\${COMPANYNAME}\${APPNAME}"
  
  ;Name and file
Name "${COMPANYNAME} - ${APPNAME}"
OutFile "${OUTDIR}\KaataZero.exe"


Caption "VersionPB www.versionpb.co.in"
VIProductVersion ${VERSIONBUILD}
VIAddVersionKey ProductName "${APPNAME}"
VIAddVersionKey FileVersion ${VERSIONBUILD}
VIAddVersionKey FileDescription "Versionpb.co.in | ( Kaata Zero Launcher )"
VIAddVersionKey LegalCopyright Versionpb.co.in
VIAddVersionKey ProductVersion ${VERSIONBUILD}

  ;--------------------------------
;Interface Configuration
  !define MUI_ICON "${IMAGEDIR}\Icon.ico"
  !define MUI_HEADERIMAGE
  !define MUI_HEADERIMAGE_BITMAP "${IMAGEDIR}\Splash.bmp" 
  !define MUI_ABORTWARNING

;--------------------------------
;Pages

  !insertmacro MUI_PAGE_WELCOME
  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_INSTFILES
  !insertmacro MUI_PAGE_FINISH

  !insertmacro MUI_UNPAGE_WELCOME
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES
  !insertmacro MUI_UNPAGE_FINISH

;--------------------------------
;Languages

  !insertmacro MUI_LANGUAGE "English"

;--------------------------------
;Installer Sections

Section "KaataZero" FilesADD
	
  # Files for the install directory - to build the installer, these should be in the same directory as the install script (this file)
	setOutPath $INSTDIR
	# Files added here should be removed by the uninstaller (see section "uninstall")
	

    file "${PROGDIR}\KaataZero.jar"
	file "${PROGDIR}\README.TXT"
	file "${IMAGEDIR}\Icon.ico"

	
	createDirectory "$SMPROGRAMS\${COMPANYNAME}"

  # Registry information for add/remove programs
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayName" "${COMPANYNAME} - ${APPNAME} - ${DESCRIPTION}"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "UninstallString" "$\"$INSTDIR\uninstall.exe$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "QuietUninstallString" "$\"$INSTDIR\uninstall.exe$\" /S"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "InstallLocation" "$\"$INSTDIR$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayIcon" "$\"$INSTDIR\KaataZero.ico$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "Publisher" "$\"${COMPANYNAME}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "HelpLink" "$\"${HELPURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "URLUpdateInfo" "$\"${UPDATEURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "URLInfoAbout" "$\"${ABOUTURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayVersion" "$\"${VERSIONMAJOR}.${VERSIONMINOR}.${VERSIONBUILD}$\""
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "VersionMajor" ${VERSIONMAJOR}
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "VersionMinor" ${VERSIONMINOR}
	# There is no option for modifying or repairing the install
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "NoModify" 1
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "NoRepair" 1
	# Set the INSTALLSIZE constant (!defined at the top of this script) so Add/Remove Programs can accurately report the size
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "EstimatedSize" ${INSTALLSIZE}


  # Uninstaller - See function un.onInit and section "uninstall" for configuration
	writeUninstaller "$INSTDIR\uninstall.exe"

SectionEnd


Section "Desktop Shortcut" DesktopShort
# Start Menu
	
	CreateShortCut "$DESKTOP\${APPNAME}.lnk" "$INSTDIR\${EXENAME}" "" "$INSTDIR\${ICONNAME}"
SectionEnd

Section "Start Menu Shortcut" StartMenuShort
# Start Menu
	
	createShortCut "$SMPROGRAMS\${COMPANYNAME}\${APPNAME}.lnk" "$INSTDIR\${EXENAME}" "" "$INSTDIR\${ICONNAME}"
SectionEnd

;--------------------------------
;Descriptions

  ;Language strings
  LangString DESC_FilesADD ${LANG_ENGLISH} "This Section contains all installables for ${APPNAME} to work properly on your machine ."
	LangString DESC_DesktopShort ${LANG_ENGLISH} "This Section contains desktop shortcut for ${APPNAME}."
	LangString DESC_StartMenuShort ${LANG_ENGLISH} "This Section contains Start Menu Shortcut for ${APPNAME} ."
  ;Assign language strings to sections
  !insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
    !insertmacro MUI_DESCRIPTION_TEXT ${FilesADD} $(DESC_FilesADD)
	!insertmacro MUI_DESCRIPTION_TEXT ${DesktopShort} $(DESC_DesktopShort)
	!insertmacro MUI_DESCRIPTION_TEXT ${StartMenuShort} $(DESC_StartMenuShort)
  !insertmacro MUI_FUNCTION_DESCRIPTION_END

;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ;ADD YOUR OWN FILES HERE...
	# Remove Start Menu launcher
	delete "$SMPROGRAMS\${COMPANYNAME}\${APPNAME}.lnk"
	# Try to remove the Start Menu folder - this will only happen if it is empty
	rmDir "$SMPROGRAMS\${COMPANYNAME}"

	# Remove files
	
	delete "$INSTDIR\KaataZero.jar"
	delete "$INSTDIR\README.TXT"
	delete "$INSTDIR\Icon.ico"
  # Always delete uninstaller as the last action
	delete $INSTDIR\uninstall.exe

	# Try to remove the install directory - this will only happen if it is empty
	rmDir $INSTDIR

	# Remove uninstaller information from the registry
	DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}"

SectionEnd

Section "Register Your Product for Free"
        execshell "open" "http://www.versionpb.co.in/register-your-product/"
SectionEnd