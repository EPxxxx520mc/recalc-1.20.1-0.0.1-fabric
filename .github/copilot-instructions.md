# Recalc Mod - AI Coding Guidelines

## Project Overview
**Recalc** is a Minecraft 1.20.1 Fabric mod that modifies game mechanics ("Reconstruction + Calculation"). The project uses **Fabric Loom** (Gradle-based) for development with data generation pipelines and structured registry patterns.

- **Mod ID**: `recalc`
- **Package**: `com.rimeveil.recalc`
- **Language**: Java 17 | **Build**: Gradle | **MC Version**: 1.20.1

## Architecture & Entry Points

### Core Initialization
- **`Recalc.java`** - Server/Common ModInitializer entry point
  - Registers items, blocks, item groups, and fuel
  - Uses `LOGGER` with mod ID for console output
  - Follows pattern: register items → item groups → blocks → special registries

- **`RecalcClient.java`** - ClientModInitializer (currently empty, for client-side features)

- **`RecalcDataGenerator.java`** - Datagen entry point
  - Registers all data providers in order: tags → lang → items → loot → models → recipes
  - Pattern: `pack.addProvider(ProviderClass::new)`

### Module Organization
```
src/main/java/com/rimeveil/recalc/
├── Item/         - Item definitions & food components
├── block/        - Block definitions with properties
├── datagen/      - Data generation providers (tags, lang, recipes, models, loot)
├── mixin/        - Currently empty (for ASM code modifications)
└── tag/          - (If used) Custom tag definitions
```

## Critical Registry Patterns

### Items (`Item/Moditem.java`)
```java
public static final Item SUPER_COAL = regitem("super_coal", new Item(new Item.Settings()));
public static final Item CORN = regitem("corn", new Item(new Item.Settings().food(ModFoodComponents.CORN)));

private static Item regitem(String id, Item item) {
    return Registry.register(Registries.ITEM, new Identifier(Recalc.MOD_ID, id), item);
}
```
- Always use `regitem()` helper; never direct Registry.register
- Food items use `ModFoodComponents` (in `Item/ModFoodComponents.java`)
- ID format: lowercase with optional prefix (e.g., `"test/example_item"`)

### Blocks (`block/Modblock.java`)
```java
public static Block register(String id, Block block) {
    regblocksitem(id, block);  // Auto-creates BlockItem
    return Registry.register(Registries.BLOCK, new Identifier(Recalc.MOD_ID, id), block);
}
```
- Always call `register()` not direct Registry calls
- Auto-generates BlockItem with same ID
- Settings: `AbstractBlock.Settings.copy(Blocks.STONE)` or `.create().strength(x, y).requiresTool()`

## Data Generation Pipeline

### Provider Execution Order (RecalcDataGenerator.java)
1. **BlockTags** - Mineable types & tool requirements
2. **EnUS/EnZh Lang** - English/Chinese translations
3. **ItemTags** - Item group assignments
4. **LootTables** - Block drops
5. **Models** - Block/item JSON models
6. **Recipes** - Crafting, smelting, smoking, campfire

### Common Datagen Patterns

**BlockTags** (pickaxe/stone_tool requirements):
```java
getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(Modblock.EXAMPLE_BLOCK);
getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(Modblock.EXAMPLE_BLOCK);
```

**Recipes** (use FabricRecipeProvider helpers):
```java
// Reversible (9 items ↔ 1 block)
offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Moditem.ITEM, 
    RecipeCategory.BUILDING_BLOCKS, Modblock.BLOCK);

// Smelting/Blasting
offerSmelting(exporter, List.of(item1, item2), category, output, xp, time, name);

// Shaped (grid-based)
ShapedRecipeJsonBuilder.create(category, output, count)
    .pattern("###").input('#', item).criterion(...).offerTo(exporter, id);

// Shapeless (unordered)
ShapelessRecipeJsonBuilder.create(category, output, count)
    .input(item1).input(item2).criterion(...).offerTo(exporter, id);
```

## Build & Development Commands

```bash
# Gradle wrapper commands (Windows: use gradlew.bat)
./gradlew build               # Full build with datagen
./gradlew runClient           # Start client in dev environment
./gradlew runServer           # Start server in dev environment
./gradlew runDatagen          # Generate data only
./gradlew eclipse             # Generate IDE configs
```

**Key Gradle Properties** (`gradle.properties`):
- `minecraft_version=1.20.1`
- `yarn_mappings=1.20.1+build.10` (deobfuscation mappings)
- `loader_version=0.18.4` (Fabric Loader)
- `loom_version=1.15-SNAPSHOT` (Fabric Loom)

## Code Conventions

### Naming
- **Item IDs**: lowercase, use `/` for categories (e.g., `test/example_item`)
- **Classes**: PascalCase prefixed with "Mod" (e.g., `ModBlockTagsProvider`)
- **Constants**: UPPER_CASE_WITH_UNDERSCORES
- **Comments**: Chinese comments preferred; use `//` for single line, `/** */` for JavaDoc

### Logging
```java
public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
LOGGER.info("message");  // Use throughout for dev visibility
```

### Imports
- Use `Identifier` not `ResourceLocation` (Fabric standard)
- Use `Registries.ITEM`, `Registries.BLOCK` (not legacy registries)
- Fabric API: `net.fabricmc.fabric.api.*`

## Testing & Validation
- Run datagen before committing: `./gradlew runDatagen`
- Verify generated files in `build/generated/data/` match expectations
- Test in both client/server modes for feature correctness
- Check console for `LOGGER` messages indicating successful registration

## Common Pitfalls
❌ Registering items/blocks outside `regitem()`/`register()` helpers  
❌ Forgetting `FuelRegistry.INSTANCE.add()` for fuel items  
❌ Not adding datagen providers to `RecalcDataGenerator.onInitializeDataGenerator()`  
❌ Using vanilla `ResourceLocation` instead of `Identifier`  
❌ Mixins in `recalc.mixins.json` without entries (currently unused)
