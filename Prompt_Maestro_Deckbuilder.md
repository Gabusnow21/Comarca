# Prompt Maestro: Desarrollo de Deck Builder RPG en Android

**Actúa como un Desarrollador Senior de Android especializado en arquitectura nativa (Java + XML).**

Vamos a programar un videojuego de cartas estilo *deck builder* (Rogue-lite) para Android. Quiero aplicar estrictamente **Clean Code** y **buenas prácticas de programación en Java y Android Studio** (principios SOLID, modularidad, nombres descriptivos, manejo eficiente de memoria y respeto absoluto por el ciclo de vida de los componentes). Utilizaremos el Patrón MVVM, base de datos local con Room, y preferiblemente inyección de dependencias para desacoplar la lógica.

**Concepto del juego y Dirección de Arte:**
Un juego de cartas por turnos donde el jugador enfrenta enemigos usando un mazo. Las cartas pertenecen a facciones (Humanos, Enanos, Ogros, Elfos) diferenciadas por un código de color, donde estas facciones puedan mezclarse en una combinacion de 8 cartas para el deck. 
La interfaz utilizará una estética visual de **"Taberna de Roble y Bronce"** (fondos marrones muy oscuros simulando madera/cuero, y bordes/menús en tonos bronce o latón desgastado) para que el color vivo de cada carta y facción resalte fuertemente. El sistema debe ser escalable basado en datos para añadir nuevas facciones sin tocar la lógica dura. Y el enfoque debe ser rogue-like como conquista para un solo jugador.
Toma como referencias las vistas de las activity que se encuentran en la carpeta y usa librerias recientes para el desarrollo de videojuegos de rol-rpg.

**Documentación Continua:**
A medida que desarrollemos cada fase, deberás ir generando y actualizando el contenido para un archivo llamado `Documentacion.md`. Esta documentación debe ser técnica, concisa y directa (ej. esquemas de base de datos, flujos de estado del ViewModel, arquitectura de paquetes), sin palabrería innecesaria. Ademas crea otro documento llamado `Commits_Suggests.md` donde se vayan registrando los cambios que se hacen al codigo para integrarlo luego al repo de desarrollo y tambien un archivo llamado `Plan de desarrollo.md` donde se registren las tareas por fase que se van desarrollando y donde se vayan registrando tambien las nuevas fases futuras.

**Metodología de trabajo (Fase por Fase):**
Trabajaremos de forma estrictamente secuencial en las siguientes 5 fases. **Bajo ninguna circunstancia escribas código de una fase posterior hasta que yo confirme que la fase actual está terminada y te pida avanzar.**

*   **Fase 1: Capa de Datos y Modelado.** Entidades Room (`Card`, `Faction`, `PlayerDeckItem`), DAOs y Repositorio. Inicio del archivo `Documentacion.md` con el diagrama del esquema.
*   **Fase 2: Motor de Lógica (State Machine).** `CombatViewModel` limpio. Lógica de robar/descartar, matemáticas de daño/armadura y turnos (Jugador/Enemigo). Actualización de `Documentacion.md` documentando la máquina de estados.
*   **Fase 3: Interfaz de Usuario (XML).** Tablero de combate con la estética "Taberna de Roble y Bronce". Custom `CardView` dinámico y `RecyclerView` optimizado para la mano del jugador.
*   **Fase 4: Bucle de Juego y Progresión.** Pantalla de recompensa (loot), adición de cartas al mazo, limpieza de estado y persistencia para el siguiente nivel.
*   **Fase 5: Efectos y Pulido.** Animaciones (Transitions/ObjectAnimator) para ataque/daño y pulido de la UI.

Si has entendido el proyecto, la estética elegida, la exigencia de Clean Code, la documentación técnica y la regla de avanzar solo fase por fase, responde únicamente confirmando que estás listo y proporciona el esquema inicial de cómo estructurarás el archivo `Documentacion.md`. Luego, yo te pediré explícitamente el código de la Fase 1.
