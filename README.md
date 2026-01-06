# Clean Architecture RPG Engine

## Project Overview

A modular, data-persistent RPG engine implemented in Java using Clean Architecture, designed for extensibility, testability, and long-term maintainability.

---

## Key Features

* **Quiz-Driven Combat System**
  
  Java knowledge is directly tied to gameplay. Players must correctly answer multiple-choice Java quizzes to attack monsters during battles.

* **Adventure Map Navigation**
  
  Players move along a fixed path on a map. Each new location may trigger a battle or an item pickup event.

* **Battle System**
  
  Turn-based combat featuring player and monster HP (Health Points), ATK (Attack), DEF (Defense), and battle logs for transparency.

* **Inventory & Item System**
  
  Items can be collected and stored in an inventory. Items provide functional effects such as healing HP, increasing defense, or boosting attack damage.

* **Automatic Save & Load**
  
  The game automatically saves progress when the player reaches new locations or exits the game, allowing seamless continuation.

* **End Game Summary**
  
  Upon reaching the final destination, players receive a detailed results screen summarizing performance metrics such as total quiz score, damage dealt, and playtime.

---

## Architecture & Design

This project strictly follows **Clean Architecture** principles:

```
View
  ↓
Controller
  ↓
Interactor (Use Case)
  ↓
Presenter
  ↓
ViewModel
  ↓
View
```

### Design Principles

* **Single Responsibility Principle (SRP)**
  Each class handles exactly one responsibility (e.g., `SubmitQuizInteractor` only manages quiz submission logic).

* **Open–Closed Principle (OCP)**
  Core abstractions (e.g., `ViewModel`) are extended rather than modified when adding new functionality.

* **Dependency Inversion Principle (DIP)**
  High-level modules depend on abstractions (interfaces), not concrete implementations.

### Design Patterns

* **Builder Pattern** – Used for application setup and configuration.
* **Factory Pattern** – Used for creating data access objects and game data sources.

---

## Core Use Cases

* Submit Quiz / Submit Unfinished Quiz
* Attack Monster
* Move on the Map
* Pick Up Item / Use Item
* Open New Game / Continue Game
* Automatic Saving & Loading
* End Game and Result Summary

Each use case is implemented with its own **Interactor**, **Controller**, **Presenter**, and **ViewModel**, ensuring strong separation of concerns.

---

## External APIs

* **Map API (Geoapify Static Maps)**
  Used to display real-world static map images corresponding to in-game locations.

* **Game Data API (D&D 5e API)**
  Provides structured data for monsters, spells, and items used in battles.

---

## Data Persistence

The game’s auto-save mechanism and external API integrations are abstracted behind interfaces. Game state persistence is handled by a repository component that writes to and reads from a JSON file, but the higher layers simply interact with a saving/loading interface (they are unaware of the file format or location). Similarly, external services (like the map image API and DnD content API) are encapsulated in service classes within an api module, keeping API-specific code separate from game logic. This modular design ensures that changes in external services or data formats have minimal impact on the overall system.

* Game state is stored locally in a JSON file (`userdata.json`).
* Autosave triggers on location changes and game exit.
* Save data is automatically deleted upon game completion to enforce a fresh restart.

---

## Project Structure

```
src/
├── api/
├── app/
├── entities/
├── use_cases/
├── interface_adapters/
├── view/
```

Each layer depends only on abstractions from inner layers, maintaining a clean and scalable codebase.

---

## UI Demonstration

To clearly demonstrate functionality and user interaction, screenshots is attached below:

* Quiz submission (before submit / after submit)

    <img width="500" height="250" alt="image" src="https://github.com/user-attachments/assets/47add562-3bd9-4739-b6af-74d9530ee064" />

* Battle view

    <img width="500" height="250" alt="image" src="https://github.com/user-attachments/assets/0d298498-b2fb-4f3c-bd74-2dd166a7b463" />

* Inventory (empty inventory / item added / item used)
  
    <img width="500" height="250" alt="image" src="https://github.com/user-attachments/assets/bf1a2ca6-a136-40ab-94f5-3bf56bbb48b4" />

* Map movement (before movement / after reaching new location)
  
    <img width="500" height="145" alt="image" src="https://github.com/user-attachments/assets/255deab7-e8b0-4504-bb59-7ef1606e97ca" />

* Open Game (new game vs. continue game)
  
    <img width="500" height="250" alt="image" src="https://github.com/user-attachments/assets/bd565d10-bbe9-4b4a-a7ed-dd45f8287e31" />

* End Game results screen
  
    <img width="500" height="250" alt="image" src="https://github.com/user-attachments/assets/548c8aed-054b-47ae-914b-1cfee071cf1d" />

---

## Team

| Team member | Contact Information                                                                            |
|-------------|------------------------------------------------------------------------------------------------|
| Deniz Coban | Outlook: d.coban@mail.utoronto.ca  <br/>Instagram: @007deno <br/>discord:                      |
| James Fan   | outlook: jl.fan@mail.utoronto.ca  <br/>Instagram: @nondescript_name <br/>Discord:              |
| Deep Garg   | outlook: deepankar.garg@mail.utoronto.ca <br/>Instagram: _deepankar.garg <br/>discord:         |
| Wentao Lin  | outlook:wentao.lin@mail.utoronto.ca <br/>Instagram:@wentao1530 <br/>discord:                   |
| Meixuan Lou (Leader) | outlook:meixuan.lou@mail.utoronto.ca <br/>Instagram: @meixuan_louol <br/>discord: hikono_louol |
| Isabel Melo | outlook: isabel.melo@mail.utoronto.ca <br/>Instagram: @mi1ce <br/>discord:                     |

---

## Conclusion

The project implements a fully decoupled game engine with Clean Architecture, featuring well-defined domain entities, use-case interactors, interface adapters, and presentation layers. Core gameplay logic — including battles, inventory management, map traversal, and quiz-driven combat resolution — is entirely isolated from UI and infrastructure concerns.

The system incorporates persistent game state management through structured data storage, enabling automatic saving, loading, and recovery across gameplay sessions. Multiple design patterns (Builder, Factory, and Dependency Inversion via abstractions) are applied to support extensibility, testability, and long-term maintainability.

By combining state persistence, external data integration, and a scalable architectural foundation, this project demonstrates the design of a maintainable, extensible RPG engine rather than a one-off application, suitable for future feature expansion or platform migration.
