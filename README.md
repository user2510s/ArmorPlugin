# 🛡️ Armadura Invisível (ArmaduraInvisivel)

Um plugin para **Minecraft Paper** que permite aos jogadores esconder visualmente partes específicas da sua armadura enquanto ainda mantêm os efeitos e atributos normalmente.

Ideal para servidores de **RPG**, **PvP estético** ou qualquer ambiente onde a aparência do personagem importa!

---

## ✨ Funcionalidades

- Oculta visualmente partes da armadura (capacete, peitoral, calça e bota)
- Comandos simples para esconder ou mostrar partes específicas
- Mudança de aparência é aplicada em tempo real (sem precisar relogar!)
- Utiliza **ProtocolLib** para interceptação de pacotes
- Compatível com **Paper 1.21.4**

---

## ⚙️ Comandos

| Comando                | Descrição                                      |
|------------------------|-----------------------------------------------|
| `/esconder`            | Esconde toda a armadura                       |
| `/esconder <parte>`    | Esconde uma parte específica (ex: capacete)   |
| `/mostrar`             | Mostra toda a armadura                        |
| `/mostrar <parte>`     | Mostra uma parte específica                   |

**Partes válidas:** `capa`, `peito`, `calça`, `bota`, `tudo`

---

## 🧩 Requisitos

- [PaperMC 1.21.4+](https://papermc.io/)
- [ProtocolLib 5.3.0+](https://www.spigotmc.org/resources/protocollib.1997/)

---

## 🧪 Compilação

Este projeto usa **Maven**. Para compilar:

```bash
mvn clean package
