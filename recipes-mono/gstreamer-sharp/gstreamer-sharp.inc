SUMMARY = "Gstreamer bindings for Mono"
DESCRIPTION = "Official gstreamer bindings for mono/.NET from gobject-introspection"
SECTION = "multimedia"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "files://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"

DEPENDS = "mono gtk-sharp3"
RDEPENDS_${PN} = "mono gtk-sharp3"

inherit autotools
inherit mono
inherit pkgconfig

# For some reason the URL template changes from version to version,
# therefore use some variables for the directory and extension
SRC_URI = "git://github.com/${PN}/${PN}.git;branch=master"

S = "${WORKDIR}/git"
