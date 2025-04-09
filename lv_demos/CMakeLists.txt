# The following lines of boilerplate have to be in your project's
# CMakeLists in this exact order for cmake to work correctly
cmake_minimum_required(VERSION 3.5)

include($ENV{IDF_PATH}/tools/cmake/project.cmake)

set(EXTRA_COMPONENT_DIRS
    ../../components
    )

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -DLV_CONF_PATH=\"${CMAKE_SOURCE_DIR}/main/lv_conf.h\"")

set(LV_CONF_INCLUDE_SIMPLE 1)

project(lv_demos)