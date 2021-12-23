require totalcross.inc

DESCRIPTION = "TotalCross Laucher Machine"

DEPENDS = "libsdl2 skia"
RDEPENDS_${PN} = "libgpiod"

inherit cmake features_check

SRC_URI += " \
    file://001-compile-fix.patch \
    file://002-cmake-fix.patch \
    "

REQUIRED_DISTRO_FEATURES = "opengl"

S = "${WORKDIR}/git/TotalCrossVM"
B = "${S}"

CXXFLAGS_append = " -I${STAGING_INCDIR}/skia/"
CFLAGS_append = " -I${STAGING_INCDIR}/skia/"

EXTRA_OEMAKE += "\
    BLDDIR='${B}' \
    SDLDIR='${STAGING_LIBDIR}' \
    SDL_INC='${STAGING_INCDIR}/SDL2' \
    SKIADIR='${STAGING_INCDIR}/skia/include' \
    SRCDIR='${S}/launchers/linux' \
    LIBS='-R${libdir}/totalcross -L. -lskia -lstdc++ -lpthread -lEGL -lfontconfig $(SDLDIR)/libSDL2main.a' \
"


# EXTRA_OECMAKE += "-DCMAKE_EXE_LINKER_FLAGS='-R${libdir}/totalcross'"
EXTRA_OECMAKE += " -DSKIA_DIR='${STAGING_INCDIR}/skia' -DSKIA_LIBRARIES=-lskia"

do_install() {
    install -Dm 0755 ${B}/Launcher ${D}${libdir}/totalcross/${PN}
}

FILES_${PN} += "${libdir}/totalcross/"

# FIXME: This is a workaround as next release uses CMake and doesn't make sense
# to spend too much time on this.
INSANE_SKIP_${PN} = "ldflags"
