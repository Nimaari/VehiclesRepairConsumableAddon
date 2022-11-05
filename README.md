# VehiclesRepairConsumableAddon

An open-source addon for the Minecraft plugin Vehicles that allows you to repair vehicles with a customizable item.

Original [plugin](https://www.spigotmc.org/resources/%E2%9C%88%EF%B8%8Fvehicles-no-resourcepacks-needed.12446/) is by Pollitoyeye, I did **NOT** make the Vehicles plugin, I only made an addon for it utilizing its API.

The plugin depends on [Vehicles](https://www.spigotmc.org/resources/%E2%9C%88%EF%B8%8Fvehicles-no-resourcepacks-needed.12446/) and softly depends on [Vault](https://www.spigotmc.org/resources/vault.34315/) (if you want to use the economy feature, you have to use Vault).

# How to use?

To repair a Vehicle, simply left-click while riding with the item you customized in the configuration file in your hand.

If you want to spawn the item in, you can simply name it in an anvil or spawn it in using other means like ``/give``.

It only checks for the name (it uses ``.contains()`` which means it only cares if a specific phrase is in the name) and for the item type itself. 

# Configuration

Here's a rundown of every entry in the configuration file for those requiring an explanation.

``sendMessageOnNotEnoughMoney: true`` Should the plugin inform you if you don't have enough money?
``sendMessageOnSuccessfulRepair: true`` Should the plugin inform you if the repair was successful?
``sendMessageOnFullHealthVehicle: true`` Should the plugin inform you if the vehicle has full health already?
``sendMessageOnInvalidVehicle: true`` Should the plugin inform you that you are not in a vehicle or is the vehicle valid?

```
notEnoughMoneyMessage: '&8[&eVehiclesRepairConsumableAddon&8] &cYou do not have enough
  money!'
successfulRepairMessage: '&8[&eVehiclesRepairConsumableAddon&8] &aSuccessful repair!'
alreadyFullHealthVehicleMessage: '&8[&eVehiclesRepairConsumableAddon&8] &cThis vehicle
  is already at full health!'
invalidVehicleMessage: '&8[&eVehiclesRepairConsumableAddon&8] &cYou are not riding
  a vehicle!'
```
This is simply the messages themselves. Colorcodes supported.

``repairPrice: 0.0`` How much should the repair cost you? If set to 0.0, it disables the economy feature.
``itemName: Repair Tool`` Name check for the item. If it contains that string of characters, it will be considered valid. WARNING! This uses **CONTAINS**, not EQUALS.
``itemMaterial: GOLDEN_PICKAXE`` Which item type should the repair item be? Click [here](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html) to see a list of valid values.
``deleteRepairItemOnRepair: true`` Should the item be removed (also works with stackable items, it decreases by 1) once the repair is complete?
