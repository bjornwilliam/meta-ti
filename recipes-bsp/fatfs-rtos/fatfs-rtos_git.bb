SUMMARY = "TI RTOS driver for FAT filesystem"

inherit ti-pdk

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://FATFS.h;beginline=1;endline=32;md5=6619832755598d1cc2b01f2e6a1801d6"

COMPATIBLE_MACHINE = "ti33x|ti43x|omap-a15|k2g|omapl1|k3"
PACKAGE_ARCH = "${MACHINE_ARCH}"

FATFS_GIT_URI = "git://git.ti.com/keystone-rtos/fatfs.git"
FATFS_GIT_PROTOCOL = "git"
FATFS_GIT_BRANCH = "master"

# Below commit ID corresponds to "DEV.FATFS_LLD.01.00.00.15"
FATFS_SRCREV = "d30f2c1492b2226a7ce31e43a72df29df5db9986"

BRANCH = "${FATFS_GIT_BRANCH}"
SRC_URI = "${FATFS_GIT_URI};protocol=${FATFS_GIT_PROTOCOL};branch=${BRANCH}"

SRCREV = "${FATFS_SRCREV}"
PV = "01.00.00.15"
PR = "r0"

DEPENDS_append = " mmcsd-lld-rtos \
                   osal-rtos \
"

# Build with make instead of XDC
TI_PDK_XDCMAKE = "0"

export PDK_FATFS_ROOT_PATH ="${WORKDIR}/build"
export DEST_ROOT="${S}"

# HTML doc link params
PDK_COMP_LINK_TEXT = "FATFS Library"
