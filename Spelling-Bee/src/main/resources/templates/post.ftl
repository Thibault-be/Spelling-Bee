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
            }

            .hex {
            width: 100px;
            height: 55px;
            background-color: #333;
            color: #ffd700;
            display: inline-block;
            margin: 5px;
            line-height: 55px;
            position: relative;
            clip-path: polygon(
            25% 0%, 75% 0%,
            100% 50%,
            75% 100%, 25% 100%,
            0% 50%
            );
            font-size: 24px;
            font-weight: bold;
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
            font-size: 16px;
            border: none;
            border-radius: 4px;
            width: 200px;
            }

            button {
            padding: 9px 16px;
            margin-left: 10px;
            background-color: #ffd700;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            }

            button:hover {
            background-color: #e6c200;
            }
        </style>

    </head>
    <body>

        <h1>Spelling Bollie</h1>

        <div>gameId: ${gameId}</div>
        <br>

        <div>Ranking: ${ranking}</div>
        <br>

        <div class="beehive">
            <div class="row">
                <#list letterLayout[0] as l><div class="hex">${l}</div></#list>
        </div>
        <div class="row">
            <#list letterLayout[1] as l><div class="hex">${l}</div></#list>
    </div>
    <div class="row">
        <#list letterLayout[2] as l><div class="hex">${l}</div></#list>
</div>
</div>

<!-- GUESS FORM -->
<div class="guess-form">
    <input type="text" id="guessInput" placeholder="Enter your guess" />
    <button onclick="submitGuess()">Try guess</button>
</div>

<script>
    function submitGuess() {
    const guess = document.getElementById('guessInput').value;
    const gameId = "${gameId?js_string}";
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
    <div><strong>Possible words:</strong></div>
    <div class="word-list">
        <#list possibleWords as word>
            <div>${word}</div>
        </#list>
    </div>

    <div class="score">Score: ${score}</div>

    <div><strong>Found words:</strong></div>
    <div class="word-list">
        <#list foundWords as word>
            <div>${word}</div>
        </#list>
    </div>
</div>

</body>
</html>



