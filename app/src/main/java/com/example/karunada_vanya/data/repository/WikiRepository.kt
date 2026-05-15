package com.example.karunada_vanya.data.repository

import com.example.karunada_vanya.data.model.WikiItem

object WikiRepository {

    // Fully offline - no network needed
    fun getAllWikiItems(): List<WikiItem> = listOf(
        WikiItem(
            id = "1",
            name = "Black Panther",
            category = "ANIMAL",
            region = "Kabini",
            description = "The Black Panther is actually a melanistic leopard found in Kabini's dense forests. Karnataka's Kabini region is one of the best places in the world to spot this rare and magnificent creature.",
            funFact = "Black Panthers are not a separate species - they are leopards with a rare genetic mutation causing excess melanin.",
            imageUrl = "black_panther",
            conservationStatus = "Vulnerable"
        ),
        WikiItem(
            id = "2",
            name = "Asian Elephant",
            category = "ANIMAL",
            region = "Bandipur",
            description = "The Asian Elephant is the largest land animal in Asia. Karnataka's Bandipur Tiger Reserve is home to one of the largest elephant populations in India.",
            funFact = "Elephants can recognize themselves in mirrors, showing self-awareness seen in very few species.",
            imageUrl = "asian_elephant",
            conservationStatus = "Endangered"
        ),
        WikiItem(
            id = "3",
            name = "Bengal Tiger",
            category = "ANIMAL",
            region = "Nagarahole",
            description = "The Bengal Tiger is India's national animal. Nagarahole National Park is part of the Nilgiri Biosphere Reserve, which hosts the world's largest tiger population.",
            funFact = "No two tigers have the same stripe pattern - just like human fingerprints.",
            imageUrl = "bengal_tiger",
            conservationStatus = "Endangered"
        ),
        WikiItem(
            id = "4",
            name = "Indian Leopard",
            category = "ANIMAL",
            region = "Bandipur",
            description = "The Indian Leopard is a powerful and elusive big cat found throughout Karnataka's forest corridors. They are excellent climbers and often haul prey into trees.",
            funFact = "Leopards are the strongest climbers among big cats and can carry prey twice their own weight up a tree.",
            imageUrl = "indian_leopard",
            conservationStatus = "Vulnerable"
        ),
        WikiItem(
            id = "5",
            name = "Malabar Giant Squirrel",
            category = "ANIMAL",
            region = "Nagarahole",
            description = "This strikingly colorful squirrel is found in the forests of the Western Ghats. With its multi-colored fur, it is one of India's most beautiful rodents.",
            funFact = "The Malabar Giant Squirrel can leap up to 6 meters between trees.",
            imageUrl = "giant_squirrel",
            conservationStatus = "Least Concern"
        ),
        WikiItem(
            id = "6",
            name = "Indian Gaur",
            category = "ANIMAL",
            region = "Kabini",
            description = "The Indian Gaur, also called the Indian Bison, is the largest bovine in the world. They are commonly seen in Kabini's grassland-forest edges.",
            funFact = "Gaur are capable of jumping fences over 1.8 meters high despite their massive size.",
            imageUrl = "indian_gaur",
            conservationStatus = "Vulnerable"
        ),
        WikiItem(
            id = "7",
            name = "Malabar Hornbill",
            category = "BIRD",
            region = "Nagarahole",
            description = "The Malabar Grey Hornbill is endemic to the Western Ghats. Its loud cackling call is a signature sound of the forest. They are important seed dispersers.",
            funFact = "The female seals herself inside a tree hollow during nesting, relying on the male to bring all food.",
            imageUrl = "malabar_hornbill",
            conservationStatus = "Least Concern"
        ),
        WikiItem(
            id = "8",
            name = "Crested Serpent Eagle",
            category = "BIRD",
            region = "Kabini",
            description = "This striking raptor is the top predator of reptiles in Karnataka's forests. Its distinctive crest and piercing call make it unmistakable.",
            funFact = "The Crested Serpent Eagle has specially adapted scales on its feet to grip slippery snakes.",
            imageUrl = "serpent_eagle",
            conservationStatus = "Least Concern"
        ),
        WikiItem(
            id = "9",
            name = "Indian Peafowl",
            category = "BIRD",
            region = "Bandipur",
            description = "India's national bird, the Peacock, thrives in Bandipur's grasslands. The brilliant plumage of the male is one of nature's most spectacular displays.",
            funFact = "The peacock's tail feathers, called a 'train', can be up to 1.5 meters long.",
            imageUrl = "peacock",
            conservationStatus = "Least Concern"
        ),
        WikiItem(
            id = "10",
            name = "Sandalwood Tree",
            category = "FLORA",
            region = "Nagarahole",
            description = "Karnataka's state tree, Sandalwood (Chandan), is one of the world's most valuable trees. Its fragrant heartwood has been used for centuries in perfumes, incense, and religious ceremonies.",
            funFact = "It takes 15-20 years for a Sandalwood tree to develop its aromatic heartwood.",
            imageUrl = "sandalwood",
            conservationStatus = "Vulnerable"
        ),
        WikiItem(
            id = "11",
            name = "Bamboo Grove",
            category = "FLORA",
            region = "Kabini",
            description = "Bamboo is the backbone of the forest ecosystem in Kabini. Giant bamboo groves provide food and shelter for elephants, bears, and countless other species.",
            funFact = "Bamboo is technically a grass and can grow up to 91 cm in a single day.",
            imageUrl = "bamboo",
            conservationStatus = "Least Concern"
        ),
        WikiItem(
            id = "12",
            name = "Wild Mango",
            category = "FLORA",
            region = "Bandipur",
            description = "The wild mango trees of Bandipur are keystone species - when they fruit, the entire forest ecosystem responds. Bears, birds, and primates all depend on them.",
            funFact = "Mangoes have been cultivated in India for over 4,000 years and are considered the 'King of Fruits'.",
            imageUrl = "wild_mango",
            conservationStatus = "Least Concern"
        )
    )

    fun getItemsByCategory(category: String): List<WikiItem> {
        if (category == "ALL") return getAllWikiItems()
        return getAllWikiItems().filter { it.category == category }
    }
}