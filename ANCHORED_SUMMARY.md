## Goal
- Implement a floating bottom navigation bar with a capsule-style tab container.

## Constraints & Preferences
- Floating nav bar must have no outer container background (content shows through underneath).
- All tabs (including Pokémon) must be rectangular with rounded corners (no circles).
- Unselected tabs show only the icon; selected tab shows icon + name in a Row.
- Selection background wraps only the selected tab's content, not covering adjacent tabs.
- Tab switch should ideally animate smoothly (desired but deferred due to `animateColorAsState` not resolving in Compose 1.10.4).
- Nav bar must be elevated/floating with proper bottom margin.
- Light and dark mode must work properly.
- Scaffold inner padding must not cause double-insets.
- No code pushed without explicit permission.

## Done
- Replaced `app_icon.jpeg` (1254×1254) with padded `app_icon.png` (1900×1900) to fix circular-mask text clipping.
- Removed animated splash screen (too much content for splash dialog).
- Implemented `FloatingNavBar.kt` using:
  - `Surface` with `shadowElevation = 12.dp`, `tonalElevation = 6.dp`, `RoundedCornerShape(24.dp)` for floating effect.
  - `Row` with `SpaceEvenly` and `Modifier.weight(1f)` via `RowScope` extension for equal-width tabs.
  - Background applied directly on selected tab via `Modifier.background(primaryContainer)` with `RoundedCornerShape(18.dp)`.
  - `AnimatedContent` for icon crossfade on selection change (250ms fadeIn/fadeOut).
  - Selected tab shows icon + label text; unselected shows only icon.
  - No explicit `Surface` color—`tonalElevation` auto-selects proper elevation tint for light/dark mode.
- Removed `innerPadding.calculateTopPadding()` from `NavHost` in `AppNavGraph.kt` to avoid double-inset issues.
- Builds successfully on `devDebug`.

## Deferred / Known Issues
- **No smooth background color animation**: `animateColorAsState` is not resolving in the current Compose version (1.10.4 via BOM `2026.02.01`). Selection background changes instantly on recomposition. Can revisit once the animation API situation is resolved.
- **FloatingNavBar uses `Icons.Default`**: missing `Settings` icon (imported from `filled.Settings` but `Icons.Default` redirect may fail depending on icon set availability). Verify at runtime.

## Key Decisions
- **Scaffold innerPadding**: Only removed from NavHost. Individual screens should handle their own system bar insets if needed.
- **No explicit Surface color**: `tonalElevation = 6.dp` handles both light/dark automatically.
- **Abandoned separate sliding indicator Box**: Simplifies layout and avoids `onGloballyPositioned` lag. Background is applied directly to the selected tab.

## Next Steps
1. Remove `materialIconsExtended` dependency if `Icons.Default.Settings` resolves at runtime.
2. Reintroduce smooth color animation once the `animateColorAsState` resolution issue is understood.
3. Push code only after user approval.

## Relevant Files
- `app/src/main/java/com/funapp/pookiemon/core/ui/components/navigation/FloatingNavBar.kt`: floating nav bar composable (direct per-tab background, no sliding indicator).
- `app/src/main/java/com/funapp/pookiemon/core/config/navigation/AppNavGraph.kt`: Scaffold + Box overlay; `innerPadding.calculateTopPadding()` removed from NavHost.
- `app/src/main/res/drawable/app_icon.png`: padded app icon (1900×1900).
- `app/src/main/java/com/funapp/pookiemon/MainActivity.kt`: calls `enableEdgeToEdge()` and `installSplashScreen()`.
