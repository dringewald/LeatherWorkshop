# LeatherWorkshop

## 🔎 Overview  
LeatherWorkshop is a plugin for Spigot that adds a **new furnace recipe** to the game, allowing **rotten flesh** to be smelted into **leather** with configurable chances, cooking speeds, and experience gains.

## 📖 Disclaimer  
I did not originally create this plugin!  
I am simply **updating it** because the **original creator** has abandoned it.  

My [community](https://discord.gg/jymDumdFVU) **loves this plugin**, so I decided to **update and improve it further**.

💡 **Original credits go to:**  
- **Joeltrauger** – The original creator of the plugin.

## 🛠️ Features  
✅ **Smelt rotten flesh into leather in a furnace**  
✅ **Customizable leather chance, cooking speed, and XP gain**  
✅ **Support for multiple languages**  
✅ **Command system to reload configuration, change language, and more**  
✅ **Full permissions support for commands and recipes**  
✅ **Supports Minecraft 1.21.4**

## 📸 Screenshots
### Smelting
![Smelting](https://github.com/dringewald/LeatherWorkshop/blob/master/github/SmeltRottenFlesh.gif?raw=true)

## 🔧 Configuration  
The plugin comes with a `config.yml` file where you can customize the following:

- **language**: Set the language for the plugin (e.g., `en`, `de`).
- **leather-chance**: Set the percentage chance (1-100) that rotten flesh will turn into leather when smelted.
- **cook-speed**: Control the smelting speed of the furnace recipe.
- **xp-gain**: Configure the experience gained when smelting rotten flesh into leather.
- 🔧 [Config file (config.yml)](https://github.com/dringewald/LeatherWorkshop/blob/master/src/main/resources/config.yml)

## 📜 Commands  
| **Command** | **Description** | **Permission** |  
|-------------|-----------------|----------------|  
| `/leatherworkshop` | Displays plugin info | `leatherworkshop.use.commands` |  
| `/leatherworkshop reload` | Reloads the plugin configuration | `leatherworkshop.reload` |  
| `/leatherworkshop language <language>` | Changes the language of the plugin | `leatherworkshop.language` |  
| `/leatherworkshop help` | Displays help information | `leatherworkshop.help` |  
| `/leatherworkshop info` | Displays plugin info | `leatherworkshop.info` |  

## 🔑 Permissions

### General Permissions  
| **Permission** | **Description** | **Default** |  
|----------------|-----------------|------------|  
| `leatherworkshop.reload` | Allows the user to reload the plugin | `op` |  
| `leatherworkshop.language` | Allows the user to change the plugin's language | `op` |  
| `leatherworkshop.help` | Allows the user to view help | `op` |  
| `leatherworkshop.info` | Allows the user to view plugin info | `true` |  

### Recipe Permissions  
| **Permission** | **Description** | **Default** |  
|----------------|-----------------|------------|  
| `leatherworkshop.recipe` | Allows access to the custom smelting recipe | `true` |  

## 📅 Change Log  
### **🆕 Latest Updates - February 04, 2025**  
- **🔄 Updated plugin for Minecraft 1.21.4 compatibility**
- **🔤 Added full language system** – All messages are now fully translatable.
- **🔨 Added Recipe permission support** Let's you use the recipe only if you have the permission "leatherworkshop.recipe".
- **🛠️ Added new admin commands** – `reload`, `language`, and more.  
- **🔄 Changed Config** – Automatic config migration for future updates.  

## 🌎 Useful Links  
- 🔗 [Original GitHub repo](https://github.com/traugdor/LeatherWorkshop)  
- 🔗 [bStats Page](https://bstats.org/plugin/bukkit/LeatherWorkshop)  
- 🔗 [Discord](https://discord.gg/jymDumdFVU)

## 🚀 Future Plans  
- Open to suggestions!  
- If you have feature requests, feel free to **open an issue** on GitHub.

## 📢 Support & Contributions  
If you enjoy this plugin, please consider **contributing or donating** to help keep development active!  

🔗 [PayPal Donation](http://paypal.me/YourPayPalLink)  

Thank you for using **LeatherWorkshop**! 🚀  
