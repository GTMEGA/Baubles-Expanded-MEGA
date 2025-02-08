plugins {
    id("fpgradle-minecraft") version ("0.10.2")
}

group = "baubles"

minecraft_fp {
    java {
        compatibility = jabel
    }

    mod {
        // Actual modid is both 'Baubles' and 'Baubles|Expanded'
        // Hardcoded here so the jar name doesn't contain a '|'
        modid = "BaublesExpanded"
        name = "Baubles Expanded"
        rootPkg = "$group"
    }

    api {
        packages = listOf("api")
    }

    core {
        coreModClass = "thaumcraft.codechicken.core.launch.DepLoader"
        coreModIgnoreRootPkg = true
    }

    tokens {
        tokenClass = "Tags"
    }
}
