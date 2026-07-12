# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
The current version is declared in `build.gradle.kts`.

## [Unreleased]

## [1.0.1] - 2026-07-12

### Added

- Optional `-Pzip` flag on `packageExe` that also produces a shareable `dist/Dittany-<version>.zip`.

### Changed

- The packaged Windows application directory is now versioned: `dist/Dittany-<version>` instead of `dist/Dittany`.

## [1.0.0] - 2026-07-12

First release of the Dittany prototype.

### Added

- Isometric, software-rendered RTS/shooter: fixed 60 Hz simulation drawing into a 320×240 framebuffer, scaled to the window.
- Nine unit classes per team (Scout, Soldier, Pyro, Demoman, Heavy, Engineer, Medic, Sniper, Spy) with class-specific weapons, plus sentry guns, pickups, and per-class respawning.
- RTS controls: click and drag selection with a green marquee, multi-unit movement orders, camera panning and drag-scrolling, minimap, and fog of war.
- AI-controlled blu team: units seek the nearest enemy through the pathfinder, hold at weapon range, and fire automatically.
- Pause/settings menu on Esc with runtime window scale (1×–5×), FPS display toggle, resume, and quit; bitmap-font text rendering (`font8x8.png`).
- Gradle build with wrapper (Java 26 toolchain): `build`, `run`, and `packageExe` for a self-contained Windows app image under `dist/Dittany`.
