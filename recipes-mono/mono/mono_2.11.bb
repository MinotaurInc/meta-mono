require mono-2.11.inc

inherit autotools pkgconfig

DEPENDS =+ "mono-native libgdiplus"

PR = "r0"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-tls=pthread --with-sigaltstack=no --with-mcs-docs=no"

do_configure_prepend() {
	autoreconf -Wcross --verbose --install --force || bbnote "mono failed to autoreconf"
	sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i acinclude.m4
	sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i libgc/acinclude.m4
	sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i eglib/acinclude.m4
}

do_install_append() {
	mkdir -p ${D}/etc/
	mkdir -p ${D}/usr/lib/
	cp -af ${STAGING_DIR_NATIVE}/etc/mono ${D}/etc/
	cp -af ${STAGING_DIR_NATIVE}/usr/lib/mono ${D}/usr/lib/
}

FILES_${PN} += "${libdir}/libikvm-native.so"
FILES_${PN} += "${libdir}/libMonoPosixHelper.so"
FILES_${PN} += "${libdir}/libMonoSupportW.so"

INSANE_SKIP_${PN} = "arch dev-so debug-files"

#
# Add patch to remove armv6 define() in atomic.h (breaks compiler for armv6)
#
SRC_URI += " file://patch-mono-atomic-armv6.patch"

#
# GNU hash missing error / Error stripping / Wrong architecture
#
# TODO: This needs fixing properly and needs to be revisited
#
INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN} += "ldflags"
TARGET_CC_ARCH += "${LDFLAGS}"

