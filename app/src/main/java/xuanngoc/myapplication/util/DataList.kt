package xuanngoc.myapplication.util

import xuanngoc.myapplication.model.*

class DataList {

    companion object {
        fun getPlantByPlantId(plants: List<Plant>, id: Int): Plant {
            plants.forEach { plant ->
                if (plant.id == id) {
                    return plant
                }
            }
            return Plant()
        }

        fun getDeviceListByGardenId(devices: List<Device>, gardenId: Int): List<Device> {
            val devicesInGarden = mutableListOf<Device>()
            devices.forEach { device ->
                if (device.gardenId == gardenId) {
                    devicesInGarden.add(device)
                }
            }
            return devicesInGarden
        }

        fun getSensorListByGardenId(sensors: List<Sensor>, gardenId: Int): List<Sensor> {
            val sensorsInGarden = mutableListOf<Sensor>()
            sensors.forEach { sensor ->
                if (sensor.gardenId == gardenId) {
                    sensorsInGarden.add(sensor)
                }
            }
            return sensorsInGarden
        }

        fun getDeviceTypeById(deviceTypes: List<DeviceType>, deviceTypeId: Int): DeviceType {
            deviceTypes.forEach { deviceType ->
                if (deviceType.id == deviceTypeId) {
                    return deviceType
                }
            }
            return DeviceType()
        }

        fun getSensorTypeById(sensorTypes: List<SensorType>, sensorTypeId: Int): SensorType {
            sensorTypes.forEach { sensorType ->
                if (sensorType.id == sensorTypeId)
                    return sensorType
            }
            return SensorType()
        }

    }

}