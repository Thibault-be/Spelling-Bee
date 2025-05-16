<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Spelling Bollie</title>
        <style>
            body {
            background-color: #1e1e1e;
            color: #ffd700;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            text-align: center;
            position: relative;
            }

            .hex {
            width: 60px;
            height: 60px;
            background-color: #333;
            color: #ffd700;
            display: inline-block;
            margin: 5px;
            line-height: 60px;
            position: relative;
            font-size: 24px;
            font-weight: bold;
            border: 1px solid #ffd700;
            border-radius: 50%;
            cursor: pointer;
            }

            .hex:hover {
            border: 1px solid #ffff00;
            background-color: #ffd700;
            color: #333;
            }

            .middle-letter {
            background-color: #ffd700;
            color: #333;
            border: 1px solid #ffff00;
            }

            .middle-letter:hover {
            background-color: #333;
            color: #ffd700;
            border: 1px solid #ffff00;
            }

            .beehive {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 40px 0;
            }

            .row {
            display: flex;
            justify-content: center;
            }

            .bottom-section {
            background-color: #2c2c2c;
            padding: 20px;
            margin-top: 40px;
            }

            .score {
            font-size: 20px;
            margin-bottom: 10px;
            }

            .word-list {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            }

            .word-list div {
            background-color: #444;
            margin: 5px;
            padding: 5px 10px;
            border-radius: 8px;
            }

            .guess-form {
            margin: 20px 0;
            }

            input[type="text"] {
            padding: 8px;
            font-size: 24px;
            border: 1px solid #ffd700;
            border-radius: 4px;
            width: 200px;
            background-color: #333;
            color: #ffd700;
            text-align: center;
            }

            button {
            padding: 9px 16px;
            margin: 5px;
            background-color: #ffd700;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            color: #333;
            }

            button:hover {
            background-color: #e6c200;
            }

            .new-game-button {
            position: absolute;
            top: 10px;
            right: 10px;
            }

            .old-games-button {
            position: absolute;
            top: 10px;
            left: 10px;
            }

            .popup {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
            }

            .popup-content {
            background-color: #2c2c2c;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #ffd700;
            width: 80%;
            max-width: 600px;
            border-radius: 8px;
            color: #ffd700;
            }

            .close {
            color: #ffd700;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
            }

            .close:hover {
            color: #e6c200;
            }

            table {
            width: 100%;
            border-collapse: collapse;
            }

            th, td {
            border: 1px solid #ffd700;
            padding: 8px;
            text-align: left;
            }

            th {
            background-color: #333;
            }

            tr:nth-child(even) {
            background-color: #444;
            }
                .previous-games-button {
                position: absolute;
                top: 10px;
                left: 10px;
                padding: 9px 16px;
                background-color: #ffd700;
                border: none;
                border-radius: 4px;
                font-weight: bold;
                cursor: pointer;
                color: #333;
                text-decoration: none; /* Remove underline */
                }

                .previous-games-button:hover {
                background-color: #e6c200;
                }
        </style>
    </head>
    <body>
        <h1>Spelling Bollie</h1>

        <div id="gameId">gameId: ${gameId}</div>
        <br>

        <div>Ranking: ${ranking}</div>
        <br>
        <div class="score">Score: ${score}</div>

        <button class="new-game-button" onclick="startNewGame()">Start New Game</button>
        <a href="/spelling-bollie/previous-games" class="previous-games-button">Previous Games</a>

        <!-- Popup Structure -->
        <div id="popup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Previous Games</h2>
                <table id="gamesTable">
                    <thead>
                        <tr>
                            <th>Game ID</th>
                            <th>Letters</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Table content will be populated by JavaScript -->
                    </tbody>
                </table>
            </div>
        </div>

        <div class="beehive">
            <div class="row">
                <#list letterLayout[0] as l>
            <div class="hex" onclick="appendLetter('${l}')">${l}</div>
        </#list>
        </div>
        <div class="row">
            <#list letterLayout[1] as l>
        <#if l_index == 1>
        <div class="hex middle-letter" onclick="appendLetter('${l}')">${l}</div>
    <#else>
        <div class="hex" onclick="appendLetter('${l}')">${l}</div>
    </#if>
    </#list>
    </div>
    <div class="row">
        <#list letterLayout[2] as l>
    <div class="hex" onclick="appendLetter('${l}')">${l}</div>
</#list>
</div>
</div>

<!-- GUESS FORM -->
<div class="guess-form">
    <input type="text" id="guessInput" placeholder="Enter your guess" />
    <div>
        <button onclick="backspace()">Backspace</button>
        <button onclick="deleteAll()">Delete All</button>
        <button onclick="submitGuess()">Try guess</button>
    </div>
</div>

<script>
    function appendLetter(letter) {
    const guessInput = document.getElementById('guessInput');
    guessInput.value += letter; // Append the clicked letter to the input box
    }

    function backspace() {
    const guessInput = document.getElementById('guessInput');
    guessInput.value = guessInput.value.slice(0, -1); // Remove the last character
    }

    function deleteAll() {
    const guessInput = document.getElementById('guessInput');
    guessInput.value = ''; // Clear the input box
    }

    function startNewGame() {
    const url = "http://localhost:8080/spelling-bollie/start-game";

    fetch(url, {
    method: 'GET'
    }).then(response => {
    if (response.ok) {
    return response.text();
    } else {
    throw new Error("Failed to start a new game.");
    }
    }).then(html => {
    document.body.innerHTML = html;
    }).catch(err => {
    console.error("Error starting new game:", err);
    alert("Error starting new game. Please try again.");
    });
    }

    function previousGames() {
    const url = "http://localhost:8080/spelling-bollie/previous-games";

    fetch(url, {
    method: 'GET'
    }).then(response => {
    if (response.ok) {
    return response.text();
    } else {
    throw new Error("Failed to get previous games.");
    }
    }).then(html => {
    document.body.innerHTML = html;
    }).catch(err => {
    console.error("Error getting previous games:", err);
    alert("Error getting the previous games..");
    });
    }

    function submitGuess() {
    const guess = document.getElementById('guessInput').value;
    const gameId = document.getElementById('gameId').textContent.split(" ")[1];
    const url = "http://localhost:8080/spelling-bollie/try-guess?guess="
    + encodeURIComponent(guess)
    + "&gameId=" + encodeURIComponent(gameId);

    fetch(url, {
    method: 'POST'
    }).then(response => {
    if (response.ok) {
    return response.text();
    } else {
    alert("Guess failed. Please try again.");
    }
    }).then(html => {
    document.body.innerHTML = html;

    document.getElementById('guessInput').addEventListener('keyup', function(event) {
    if (event.key === 'Enter') {
    submitGuess();
    }
    });

    // Update the URL without reloading the page
    history.pushState({}, '', `/spelling-bollie/game?gameId=${gameId}`);
    }).catch(err => {
    alert("Error sending guess.");
    });
    }
</script>

<#if lastGuess??>
    <div>Last guess: <strong>${lastGuess}</strong></div>
</#if>

<div class="bottom-section">
    <div><strong>Found words:</strong></div>
    <div class="word-list">
        <#list foundWords as word>
            <div>${word}</div>
        </#list>
    </div>
</div>
</body>
</html>