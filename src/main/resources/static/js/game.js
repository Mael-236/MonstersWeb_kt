// Game Data
const MONSTERS = {
    charbouk: { name: 'Charbouk', type: 'Feu', hp: 45, atk: 12, def: 8, spd: 10, sprite: '🔥', rarity: 'starter' },
    aquabri: { name: 'Aquabri', type: 'Eau', hp: 50, atk: 10, def: 10, spd: 8, sprite: '💧', rarity: 'starter' },
    brambou: { name: 'Brambou', type: 'Plante', hp: 55, atk: 11, def: 12, spd: 6, sprite: '🌿', rarity: 'starter' },
    flamby: { name: 'Flamby', type: 'Feu', hp: 45, atk: 12, def: 8, spd: 10, sprite: '🔥', rarity: 'common' },
    aqualis: { name: 'Aqualis', type: 'Eau', hp: 50, atk: 10, def: 10, spd: 8, sprite: '💧', rarity: 'common' },
    terrak: { name: 'Terrak', type: 'Terre', hp: 55, atk: 11, def: 12, spd: 6, sprite: '🪨', rarity: 'common' },
    voltix: { name: 'Voltix', type: 'Électrique', hp: 40, atk: 13, def: 7, spd: 12, sprite: '⚡', rarity: 'uncommon' },
    glaceon: { name: 'Glaceon', type: 'Glace', hp: 48, atk: 10, def: 9, spd: 9, sprite: '❄️', rarity: 'uncommon' },
    shadowfang: { name: 'Shadowfang', type: 'Ténèbres', hp: 52, atk: 14, def: 8, spd: 11, sprite: '🌙', rarity: 'rare' },
    dragonix: { name: 'Dragonix', type: 'Dragon', hp: 60, atk: 15, def: 10, spd: 13, sprite: '🐉', rarity: 'legendary' }
};

const ZONES = [
    { id: 'forest', name: 'Forêt Mystique', monsters: ['flamby', 'terrak', 'brambou'], level: 1, minLevel: 1 },
    { id: 'lake', name: 'Lac Cristal', monsters: ['aqualis', 'glaceon', 'aquabri'], level: 2, minLevel: 3 },
    { id: 'mountain', name: 'Mont Tonnerre', monsters: ['voltix', 'terrak', 'shadowfang'], level: 3, minLevel: 5 },
    { id: 'cave', name: 'Grotte Sombre', monsters: ['shadowfang', 'terrak', 'dragonix'], level: 5, minLevel: 8 }
];

const ITEMS = {
    potion: { name: 'Potion', price: 50, effect: 'heal', value: 20, description: 'Restaure 20 HP' },
    superPotion: { name: 'Super Potion', price: 100, effect: 'heal', value: 50, description: 'Restaure 50 HP' },
    hyperPotion: { name: 'Hyper Potion', price: 200, effect: 'heal', value: 100, description: 'Restaure 100 HP' },
    monsterBall: { name: 'Monster Ball', price: 200, effect: 'capture', value: 0.3, description: 'Capture un monstre affaibli' },
    superBall: { name: 'Super Ball', price: 400, effect: 'capture', value: 0.5, description: 'Meilleure chance de capture' },
    revive: { name: 'Rappel', price: 150, effect: 'revive', value: 0.5, description: 'Ranime un monstre KO avec 50% HP' }
};

const TYPE_EFFECTIVENESS = {
    'Feu': { 'Plante': 2, 'Eau': 0.5, 'Glace': 2 },
    'Eau': { 'Feu': 2, 'Plante': 0.5, 'Terre': 2 },
    'Plante': { 'Eau': 2, 'Feu': 0.5, 'Terre': 2 },
    'Électrique': { 'Eau': 2, 'Terre': 0.5 },
    'Glace': { 'Plante': 2, 'Feu': 0.5, 'Dragon': 2 },
    'Ténèbres': { 'Normal': 1.5 },
    'Dragon': { 'Dragon': 2 },
    'Terre': { 'Feu': 2, 'Électrique': 2 }
};

// Game State
let gameState = {
    currentUser: null,
    users: {},
    player: { name: '', level: 1, money: 500, exp: 0, badges: 0 },
    team: [],
    box: [], // Stockage des monstres supplémentaires
    inventory: { potion: 3, superPotion: 1, monsterBall: 5, superBall: 0, hyperPotion: 0, revive: 0 },
    currentZone: null,
    gameStarted: false,
    battleCount: 0,
    capturedMonsters: 0
};

let battleState = null;

// Initialize Game
function initGame() {
    loadUsers();
    if (gameState.currentUser && gameState.gameStarted) {
        showNavbar();
        navigateTo('exploration');
    } else {
        hideNavbar();
    }
    updateInventoryButtons();
}

// User Management
function loadUsers() {
    const usersData = localStorage.getItem('monstersWebUsers');
    if (usersData) {
        gameState.users = JSON.parse(usersData);
    }
}

function saveUsers() {
    localStorage.setItem('monstersWebUsers', JSON.stringify(gameState.users));
}

function login() {
    const username = document.getElementById('login-username')?.value.trim();
    const password = document.getElementById('login-password')?.value.trim();

    if (!username || !password) {
        showMessage('⚠️ Veuillez remplir tous les champs');
        return;
    }

    if (gameState.users[username] && gameState.users[username].password === password) {
        localStorage.setItem('currentUser', username);
        loadGame();
        showMessage('✅ Connexion réussie !');
        setTimeout(() => {
            window.location.href = '../templates/player.html';
        }, 1000);
    } else {
        showMessage('❌ Nom d\'utilisateur ou mot de passe incorrect');
    }
}

function register() {
    const username = document.getElementById('register-username')?.value.trim();
    const password = document.getElementById('register-password')?.value.trim();
    const confirmPassword = document.getElementById('register-confirm')?.value.trim();

    if (!username || !password || !confirmPassword) {
        showMessage('⚠️ Veuillez remplir tous les champs');
        return;
    }

    if (password !== confirmPassword) {
        showMessage('❌ Les mots de passe ne correspondent pas');
        return;
    }

    if (password.length < 4) {
        showMessage('❌ Le mot de passe doit contenir au moins 4 caractères');
        return;
    }

    if (gameState.users[username]) {
        showMessage('❌ Ce nom d\'utilisateur existe déjà');
        return;
    }

    gameState.users[username] = {
        password: password,
        savedGame: null
    };
    localStorage.setItem('currentUser', username);
    saveUsers();
    showMessage('✅ Compte créé avec succès ! Redirection...');
    
    setTimeout(() => {
        window.location.href = '../templates/depart.html';
    }, 1000);
}

function logout() {
    if (confirm('Voulez-vous vous déconnecter ?')) {
        saveGame();
        gameState.currentUser = null;
        gameState.gameStarted = false;
        localStorage.removeItem('currentUser');
        window.location.href = 'index.html';
    }
}

// Save/Load Game
function saveGame() {
    if (!gameState.currentUser) return;
    
    const saveData = {
        player: gameState.player,
        team: gameState.team,
        box: gameState.box,
        inventory: gameState.inventory,
        gameStarted: gameState.gameStarted,
        battleCount: gameState.battleCount,
        capturedMonsters: gameState.capturedMonsters
    };
    
    gameState.users[gameState.currentUser].savedGame = saveData;
    saveUsers();
}

function loadGame() {
    if (!gameState.currentUser || !gameState.users[gameState.currentUser].savedGame) return;
    
    const saveData = gameState.users[gameState.currentUser].savedGame;
    gameState.player = saveData.player;
    gameState.team = saveData.team;
    gameState.box = saveData.box || [];
    gameState.inventory = saveData.inventory;
    gameState.gameStarted = saveData.gameStarted;
    gameState.battleCount = saveData.battleCount || 0;
    gameState.capturedMonsters = saveData.capturedMonsters || 0;
}

// Navigation
function navigateTo(page) {
    const pages = document.querySelectorAll('.page');
    pages.forEach(p => p.style.display = 'none');
    
    const targetPage = document.getElementById(`${page}-page`);
    if (targetPage) {
        targetPage.style.display = 'block';
    }
    
    if (page === 'exploration') renderExploration();
    if (page === 'team') renderTeam();
    if (page === 'inventory') renderInventory();
}

function showNavbar() {
    const navbar = document.getElementById('navbar');
    const connecte = document.getElementById('connecte');
    if (navbar) navbar.style.display = 'flex';
    if (connecte) connecte.style.display = 'flex';
}

function hideNavbar() {
    const navbar = document.getElementById('navbar');
    if (navbar) navbar.style.display = 'none';
}

function resetGame() {
    if (confirm('Recommencer ? La sauvegarde actuelle sera perdue.')) {
        if (gameState.currentUser) {
            gameState.users[gameState.currentUser].savedGame = null;
            saveUsers();
        }
        location.reload();
    }
}

// Render Functions
function renderStarters() {
    const startersDiv = document.getElementById('starters');
    if (!startersDiv) return;
    
    const starters = ['charbouk', 'aquabri', 'brambou'];
    
    startersDiv.innerHTML = starters.map((key, index) => {
        const monster = MONSTERS[key];
        return `
            <div class="starter-card">
                <div style="position: absolute; top: 10px; left: 10px; font-size: 2rem; font-weight: bold; background: rgba(0,0,0,0.5); width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center;">
                    ${index + 1}
                </div>
                <div class="monster-sprite-large">${monster.sprite}</div>
                <h3>${monster.name}</h3>
                <p class="monster-type">${monster.type}</p>
                <div class="stats">
                    <span>❤️ ${monster.hp}</span>
                    <span>⚔️ ${monster.atk}</span>
                    <span>🛡️ ${monster.def}</span>
                    <span>⚡ ${monster.spd}</span>
                </div>
            </div>
        `;
    }).join('');
}

function selectStarter(choice) {
    const starterKeys = ['charbouk', 'aquabri', 'brambou'];
    const choiceNum = parseInt(choice);
    
    if (isNaN(choiceNum) || choiceNum < 1 || choiceNum > 3) {
        showMessage('❌ Choix invalide ! Entrez 1, 2 ou 3');
        return;
    }
    
    const starterKey = starterKeys[choiceNum - 1];
    startGame(starterKey);
}

function startGame(starterKey) {
    const starter = { ...MONSTERS[starterKey], level: 5, exp: 0, id: Date.now() };
    starter.maxHp = Math.floor(starter.hp * (1 + starter.level * 0.1));
    starter.currentHp = starter.maxHp;

    gameState.player.name = gameState.currentUser;
    gameState.team = [starter];
    gameState.box = [];
    gameState.gameStarted = true;

    // Sauvegarder l'utilisateur actuel dans localStorage
    localStorage.setItem('currentUser', gameState.currentUser);
    saveGame();

    showMessage(`🎉 Bienvenue ${gameState.player.name} ! ${starter.name} vous accompagnera dans votre aventure !`);

    setTimeout(() => {
        window.location.href = 'exploration.html';
    }, 2000);
}

function renderExploration() {
    const playerInfo = document.getElementById('player-info');
    if (playerInfo) {
        playerInfo.innerHTML = `
            <p>👤 ${gameState.player.name} - Niveau ${gameState.player.level}</p>
            <p>💰 ${gameState.player.money}€</p>
            <p>🎖️ Badges: ${gameState.player.badges}</p>
            <p>📊 Combats: ${gameState.battleCount} | Captures: ${gameState.capturedMonsters}</p>
        `;
    }

    const zonesDiv = document.getElementById('zones');
    if (zonesDiv) {
        zonesDiv.innerHTML = ZONES.map(zone => {
            const teamLevel = gameState.team[0]?.level || 1;
            const isLocked = teamLevel < zone.minLevel;
            
            return `
                <div class="zone-card ${isLocked ? 'locked' : ''}" onclick="${isLocked ? '' : `startBattle('${zone.id}')`}">
                    <h3>${zone.name}</h3>
                    <p>Niveau recommandé: ${zone.level}</p>
                    ${isLocked ? `<p style="color: #ff6b6b;">🔒 Niveau ${zone.minLevel} requis</p>` : ''}
                    <p>Monstres: ${zone.monsters.map(m => MONSTERS[m].sprite).join(' ')}</p>
                </div>
            `;
        }).join('');
    }
}

function renderTeam() {
    const teamGrid = document.getElementById('team-grid');
    if (!teamGrid) return;
    
    if (gameState.team.length === 0) {
        teamGrid.innerHTML = '<p>Aucun monstre dans l\'équipe</p>';
        return;
    }

    teamGrid.innerHTML = gameState.team.map((monster, i) => {
        const expPercent = (monster.exp / (monster.level * 100)) * 100;
        const currentHp = monster.currentHp || monster.maxHp;
        const hpPercent = (currentHp / monster.maxHp) * 100;
        
        return `
            <div class="team-monster">
                <div class="monster-card">
                    <div class="monster-sprite">${monster.sprite}</div>
                    <h3>${monster.name} ${monster.level > 10 ? '⭐' : ''}</h3>
                    <p class="monster-type">${monster.type}</p>
                    <p>Niveau ${monster.level}</p>
                    <div class="hp-bar">
                        <div class="hp-fill player" style="width: ${hpPercent}%"></div>
                    </div>
                    <p>${currentHp} / ${monster.maxHp} HP</p>
                    <div class="stats">
                        <span>⚔️ ${Math.floor(monster.atk * (1 + monster.level * 0.1))}</span>
                        <span>🛡️ ${Math.floor(monster.def * (1 + monster.level * 0.1))}</span>
                        <span>⚡ ${monster.spd}</span>
                    </div>
                    ${i > 0 ? `<button onclick="removeFromTeam(${i})" style="margin-top: 10px;">📦 Ranger</button>` : ''}
                </div>
                <div class="exp-bar">
                    <div class="exp-fill" style="width: ${expPercent}%"></div>
                </div>
                <p>EXP: ${monster.exp} / ${monster.level * 100}</p>
            </div>
        `;
    }).join('');

    function renderPlayerMonster() {
        const playerMonsterDiv = document.getElementById('player-monster');
        if (!playerMonsterDiv || gameState.team.length === 0) return;

        const monster = gameState.team[0];
        const currentHp = monster.currentHp || monster.maxHp;
        const hpPercent = (currentHp / monster.maxHp) * 100;

        playerMonsterDiv.innerHTML = `
        <div class="monster-sprite">${monster.sprite}</div>
        <h3>${monster.name}</h3>
        <p class="monster-type">${monster.type}</p>
        <p>Niveau ${monster.level}</p>
        <div class="stats">
            <span>⚔️ ${Math.floor(monster.atk * (1 + monster.level * 0.1))}</span>
            <span>🛡️ ${Math.floor(monster.def * (1 + monster.level * 0.1))}</span>
            <span>⚡ ${monster.spd}</span>
        </div>
    `;

        const playerHpBar = document.getElementById('player-hp');
        const playerHpText = document.getElementById('player-hp-text');
        if (playerHpBar && playerHpText) {
            playerHpBar.style.width = `${hpPercent}%`;
            playerHpText.textContent = `${currentHp} / ${monster.maxHp} HP`;
        }
    }

    // Afficher le PC (Box)
    const boxSection = document.getElementById('box-section');
    if (boxSection && gameState.box.length > 0) {
        boxSection.innerHTML = `
            <h3>📦 PC (${gameState.box.length} monstres)</h3>
            <div class="box-grid">
                ${gameState.box.map((monster, i) => `
                    <div class="box-monster" onclick="addToTeam(${i})">
                        <div class="monster-sprite">${monster.sprite}</div>
                        <p>${monster.name}</p>
                        <p>Niv. ${monster.level}</p>
                    </div>
                `).join('')}
            </div>
        `;
    }
}

function removeFromTeam(index) {
    if (gameState.team.length <= 1) {
        showMessage('❌ Vous devez garder au moins un monstre dans votre équipe');
        return;
    }
    
    const monster = gameState.team.splice(index, 1)[0];
    gameState.box.push(monster);
    saveGame();
    renderTeam();
    showMessage(`${monster.name} a été rangé dans le PC`);
}

function addToTeam(boxIndex) {
    if (gameState.team.length >= 6) {
        showMessage('❌ Votre équipe est complète (max 6 monstres)');
        return;
    }
    
    const monster = gameState.box.splice(boxIndex, 1)[0];
    gameState.team.push(monster);
    saveGame();
    renderTeam();
    showMessage(`${monster.name} a rejoint votre équipe !`);
}

function renderInventory() {
    const inventoryGrid = document.getElementById('inventory-grid');
    if (!inventoryGrid) return;
    
    inventoryGrid.innerHTML = Object.entries(gameState.inventory).map(([key, qty]) => {
        const item = ITEMS[key];
        if (!item) return '';
        
        return `
            <div class="inventory-item">
                <h3>${item.name}</h3>
                <p class="item-qty">Quantité: ${qty}</p>
                <p class="item-desc">${item.description}</p>
                <p class="item-price">Prix: ${item.price}€</p>
            </div>
        `;
    }).join('');
}

// Battle Functions
function startBattle(zoneId) {
    const zone = ZONES.find(z => z.id === zoneId);
    if (!zone) return;
    
    // Vérifier si le joueur a un monstre vivant
    const aliveMonster = gameState.team.find(m => (m.currentHp || m.maxHp) > 0);
    if (!aliveMonster) {
        showMessage('❌ Tous vos monstres sont KO ! Allez les soigner.');
        return;
    }
    
    const randomMonster = zone.monsters[Math.floor(Math.random() * zone.monsters.length)];
    const enemy = { ...MONSTERS[randomMonster], level: zone.level + Math.floor(Math.random() * 3), id: Date.now() };
    enemy.maxHp = Math.floor(enemy.hp * (1 + enemy.level * 0.1));
    enemy.currentHp = enemy.maxHp;

    const playerMonster = { ...aliveMonster };
    if (!playerMonster.currentHp) playerMonster.currentHp = playerMonster.maxHp;

    battleState = {
        enemy,
        playerMonster,
        playerMonsterIndex: gameState.team.findIndex(m => m.id === playerMonster.id),
        turn: 'player',
        canCapture: false,
        turnCount: 0
    };

    gameState.battleCount++;
    saveGame();

    window.location.href = 'battle.html';
}

function renderBattle() {
    if (!battleState) return;
    
    const enemy = battleState.enemy;
    const player = battleState.playerMonster;

    const enemyMonsterDiv = document.getElementById('enemy-monster');
    if (enemyMonsterDiv) {
        enemyMonsterDiv.innerHTML = `
            <div class="monster-sprite">${enemy.sprite}</div>
            <h3>${enemy.name}</h3>
            <p class="monster-type">${enemy.type}</p>
            <p>Niveau ${enemy.level}</p>
        `;
    }

    const playerMonsterDiv = document.getElementById('player-monster');
    if (playerMonsterDiv) {
        playerMonsterDiv.innerHTML = `
            <div class="monster-sprite">${player.sprite}</div>
            <h3>${player.name}</h3>
            <p class="monster-type">${player.type}</p>
            <p>Niveau ${player.level}</p>
        `;
    }

    updateBattleUI();
}

function updateBattleUI() {
    if (!battleState) return;
    
    const enemy = battleState.enemy;
    const player = battleState.playerMonster;

    const enemyHpBar = document.getElementById('enemy-hp');
    const enemyHpText = document.getElementById('enemy-hp-text');
    if (enemyHpBar && enemyHpText) {
        const enemyHpPercent = (enemy.currentHp / enemy.maxHp) * 100;
        enemyHpBar.style.width = `${enemyHpPercent}%`;
        enemyHpText.textContent = `${enemy.currentHp} / ${enemy.maxHp} HP`;
    }

    const playerHpBar = document.getElementById('player-hp');
    const playerHpText = document.getElementById('player-hp-text');
    if (playerHpBar && playerHpText) {
        const playerHpPercent = (player.currentHp / player.maxHp) * 100;
        playerHpBar.style.width = `${playerHpPercent}%`;
        playerHpText.textContent = `${player.currentHp} / ${player.maxHp} HP`;
    }

    updateInventoryButtons();
}

function updateInventoryButtons() {
    const btnPotion = document.getElementById('btn-potion');
    const btnCapture = document.getElementById('btn-capture');
    
    if (btnPotion) {
        btnPotion.textContent = `🧪 Potion (${gameState.inventory.potion})`;
        btnPotion.disabled = gameState.inventory.potion === 0;
    }
    
    if (btnCapture) {
        btnCapture.textContent = `⚾ Capturer (${gameState.inventory.monsterBall})`;
        btnCapture.disabled = !battleState?.canCapture || gameState.inventory.monsterBall === 0;
    }
}

function getTypeEffectiveness(attackerType, defenderType) {
    if (TYPE_EFFECTIVENESS[attackerType] && TYPE_EFFECTIVENESS[attackerType][defenderType]) {
        return TYPE_EFFECTIVENESS[attackerType][defenderType];
    }
    return 1;
}

function attack() {
    if (!battleState || battleState.turn !== 'player') return;

    const attacker = battleState.playerMonster;
    const defender = battleState.enemy;
    
    const effectiveness = getTypeEffectiveness(attacker.type, defender.type);
    const baseDamage = Math.floor(attacker.atk * (1 + attacker.level * 0.1)) - Math.floor(defender.def * (1 + defender.level * 0.05));
    const damage = Math.max(1, Math.floor(baseDamage * effectiveness * (0.85 + Math.random() * 0.15)));

    defender.currentHp = Math.max(0, defender.currentHp - damage);
    
    let message = `${attacker.name} inflige ${damage} dégâts !`;
    if (effectiveness > 1) message += ' C\'est super efficace ! 💥';
    if (effectiveness < 1) message += ' Ce n\'est pas très efficace...';
    
    showBattleMessage(message);
    updateBattleUI();

    if (defender.currentHp === 0) {
        setTimeout(() => endBattle(true), 1500);
        return;
    }

    battleState.canCapture = defender.currentHp < defender.maxHp * 0.5;
    battleState.turn = 'enemy';
    battleState.turnCount++;
    
    setTimeout(enemyTurn, 1500);
}

function enemyTurn() {
    if (!battleState) return;
    
    const attacker = battleState.enemy;
    const defender = battleState.playerMonster;
    
    const effectiveness = getTypeEffectiveness(attacker.type, defender.type);
    const baseDamage = Math.floor(attacker.atk * (1 + attacker.level * 0.1)) - Math.floor(defender.def * (1 + defender.level * 0.05));
    const damage = Math.max(1, Math.floor(baseDamage * effectiveness * (0.85 + Math.random() * 0.15)));

    defender.currentHp = Math.max(0, defender.currentHp - damage);
    
    let message = `${attacker.name} inflige ${damage} dégâts !`;
    if (effectiveness > 1) message += ' C\'est super efficace ! 💥';
    if (effectiveness < 1) message += ' Ce n\'est pas très efficace...';
    
    showBattleMessage(message);
    updateBattleUI();

    if (defender.currentHp === 0) {
        setTimeout(() => endBattle(false), 1500);
        return;
    }

    battleState.turn = 'player';
}

function useItem(itemKey) {
    if (!battleState || battleState.turn !== 'player' || gameState.inventory[itemKey] <= 0) return;

    const item = ITEMS[itemKey];
    gameState.inventory[itemKey]--;

    if (item.effect === 'heal') {
        const healed = Math.min(
            battleState.playerMonster.maxHp - battleState.playerMonster.currentHp,
            item.value
        );
        battleState.playerMonster.currentHp += healed;
        
        showBattleMessage(`${gameState.player.name} utilise ${item.name} ! +${healed} HP`);
        saveGame();
        updateBattleUI();
        
        battleState.turn = 'enemy';
        setTimeout(enemyTurn, 1500);
    } else if (item.effect === 'capture') {
        const hpFactor = 1 - (battleState.enemy.currentHp / battleState.enemy.maxHp);
        const catchRate = item.value * hpFactor * (1 + battleState.turnCount * 0.05);
        const caught = Math.random() < catchRate;

        if (caught) {
            showBattleMessage(`${battleState.enemy.name} capturé !`);
            
            const newMonster = { 
                ...MONSTERS[Object.keys(MONSTERS).find(k => MONSTERS[k].name === battleState.enemy.name)], 
                level: battleState.enemy.level,
                exp: 0,
                id: Date.now()
            };
            newMonster.maxHp = battleState.enemy.maxHp;
            newMonster.currentHp = battleState.enemy.currentHp;
            
            if (gameState.team.length < 6) {
                gameState.team.push(newMonster);
            } else {
                gameState.box.push(newMonster);
                showBattleMessage(`${newMonster.name} envoyé au PC (équipe complète)`);
            }
            
            gameState.capturedMonsters++;
            saveGame();
            setTimeout(() => endBattle(true, true), 1500);
        } else {
            showBattleMessage(`${battleState.enemy.name} s'est échappé !`);
            saveGame();
            updateBattleUI();
            battleState.turn = 'enemy';
            setTimeout(enemyTurn, 1500);
        }
    }
}

function flee() {
    if (!battleState) return;
    
    const fleeChance = 0.5 + (battleState.playerMonster.spd / (battleState.playerMonster.spd + battleState.enemy.spd)) * 0.5;
    
    if (Math.random() < fleeChance) {
        showBattleMessage('Fuite réussie ! 🏃');
        setTimeout(() => {
            battleState = null;
            window.location.href = 'exploration.html';
        }, 1500);
    } else {
        showBattleMessage('Impossible de fuir ! ❌');
        battleState.turn = 'enemy';
        setTimeout(enemyTurn, 1500);
    }
}

function endBattle(victory, captured = false) {
    if (!battleState) return;
    
    // Mettre à jour les HP du monstre du joueur
    gameState.team[battleState.playerMonsterIndex].currentHp = battleState.playerMonster.currentHp;
    
    if (victory && !captured) {
        const expGained = Math.floor(battleState.enemy.level * 50 * (1 + Math.random() * 0.5));
        const moneyGained = Math.floor(battleState.enemy.level * 20);
        
        gameState.team[battleState.playerMonsterIndex].exp += expGained;
        gameState.player.money += moneyGained;
        
        // Level up check
        const monster = gameState.team[battleState.playerMonsterIndex];
        while (monster.exp >= monster.level * 100) {
            monster.level++;
            monster.exp -= (monster.level - 1) * 100;
            monster.maxHp = Math.floor(monster.hp * (1 + monster.level * 0.1));
            monster.currentHp = monster.maxHp;
            showBattleMessage(`🎉 ${monster.name} monte au niveau ${monster.level} !`);
        }
        
        showBattleMessage(`Victoire ! +${expGained} EXP, +${moneyGained}€ 🏆`);
        saveGame();
        
        setTimeout(() => {
            battleState = null;
            window.location.href = 'exploration.html';
        }, 2500);
    } else if (captured) {
        saveGame();
        setTimeout(() => {
            battleState = null;
            window.location.href = 'exploration.html';
        }, 2000);
    } else {
        showBattleMessage('Votre monstre est KO ! 😢');
        
        // Vérifier si d'autres monstres sont disponibles
        const nextMonster = gameState.team.find(m => m.currentHp > 0);
        
        if (nextMonster) {
            setTimeout(() => {
                showBattleMessage(`Envoyez ${nextMonster.name} au combat !`);
                battleState.playerMonster = { ...nextMonster };
                battleState.playerMonsterIndex = gameState.team.findIndex(m => m.id === nextMonster.id);
                battleState.turn = 'player';
                renderBattle();
            }, 2000);
        } else {
            showBattleMessage('Tous vos monstres sont KO ! Retour à la ville... 💫');
            gameState.player.money = Math.floor(gameState.player.money * 0.5);
            saveGame();
            
            setTimeout(() => {
                battleState = null;
                window.location.href = 'exploration.html';
            }, 2500);
        }
    }
}

// Town Functions
function healTeam() {
    if (gameState.player.money < 100) {
        showMessage('❌ Pas assez d\'argent ! (100€ requis)');
        return;
    }

    gameState.player.money -= 100;
    gameState.team.forEach(monster => {
        monster.currentHp = monster.maxHp;
    });
    
    saveGame();
    showMessage('✨ Votre équipe a été entièrement soignée !');
    renderExploration();
}

function buyItem(itemKey) {
    const item = ITEMS[itemKey];
    if (!item) return;

    if (gameState.player.money < item.price) {
        showMessage(`❌ Pas assez d'argent ! (${item.price}€ requis)`);
        return;
    }

    gameState.player.money -= item.price;
    gameState.inventory[itemKey]++;
    
    saveGame();
    showMessage(`✅ ${item.name} acheté ! (${gameState.inventory[itemKey]} en stock)`);
    renderExploration();
}

// Message Functions
function showMessage(text) {
    const popup = document.getElementById('message-popup');
    if (popup) {
        popup.textContent = text;
        popup.style.display = 'block';
        setTimeout(() => {
            popup.style.display = 'none';
        }, 3000);
    } else {
        alert(text);
    }
}

function showBattleMessage(text) {
    const messageDiv = document.getElementById('battle-message');
    if (messageDiv) {
        messageDiv.textContent = text;
        messageDiv.style.display = 'block';
        setTimeout(() => {
            messageDiv.style.display = 'none';
        }, 1500);
    }
}

// Initialisation automatique selon la page
document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname;

    // Charger l'utilisateur si connecté
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
        gameState.currentUser = currentUser;
        loadUsers();
        loadGame();
    }

    // Auto-render selon la page
    if (currentPath.includes('exploration.html')) {
        if (gameState.gameStarted) {
            renderExploration();
        } else {
            window.location.href = 'depart.html';
        }
    } else if (currentPath.includes('player.html')) {
        if (gameState.team.length > 0) {
            renderPlayerMonster();
        }
    } else if (currentPath.includes('battle.html')) {
        if (battleState) {
            renderBattle();
        } else {
            window.location.href = 'exploration.html';
        }
    } else if (currentPath.includes('depart.html')) {
        if (gameState.gameStarted) {
            window.location.href = 'exploration.html';
        } else {
            renderStarters();
        }
    } else if (currentPath.includes('team.html')) {
        renderTeam();
    } else if (currentPath.includes('inventory.html')) {
        renderInventory();
    }
});