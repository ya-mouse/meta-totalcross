require totalcross.inc

DESCRIPTION = "TotalCross Virtual Machine"

DEPENDS = "libsdl2 skia"

inherit cmake features_check

REQUIRED_DISTRO_FEATURES = "opengl"

S = "${WORKDIR}/git/TotalCrossVM"
B = "${S}"

SRC_URI += " \
    file://001-compile-fix.patch \
    file://002-cmake-fix.patch \
    "

inherit lib_package

CXXFLAGS_append = " -I${STAGING_INCDIR}/skia/ -DPNG_ARM_NEON_OPT=0"
CFLAGS_append = " -I${STAGING_INCDIR}/skia/ -DPNG_ARM_NEON_OPT=0"

EXTRA_OEMAKE += "\
    BLDDIR='${B}' \
    SDLDIR='${STAGING_LIBDIR}' \
    SDL_INC='${STAGING_INCDIR}/SDL2' \
    SKIADIR='${STAGING_INCDIR}/skia/include' \
    SRCDIR='${S}' \
"

# EXTRA_OECMAKE += "-DCMAKE_EXE_LINKER_FLAGS='-R${libdir}/totalcross'"
EXTRA_OECMAKE += " -DSKIA_DIR='${STAGING_INCDIR}/skia' -DSKIA_LIBRARIES=-lskia"


SECURITY_STRINGFORMAT = ""

do_install() {
    install -Dm 0755 ${B}/libtcvm.so ${D}${libdir}/totalcross/libtcvm.so.1
    ln -sf libtcvm.so.1 ${D}${libdir}/totalcross/libtcvm.so
}

#dev package rename
FILES_${PN} += "${libdir}/totalcross/"
INSANE_SKIP_${PN} += "dev-so"

# FIXME: This is a workaround as next release uses CMake and doesn't make sense
# to spend too much time on this.
INSANE_SKIP_${PN} += "ldflags"
