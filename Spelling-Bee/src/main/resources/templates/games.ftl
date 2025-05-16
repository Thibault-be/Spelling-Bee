<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Previous Games</title>
        <style>
            body {
            background-color: #1e1e1e;
            color: #ffd700;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            text-align: center;
            }

            h1 {
            color: #ffd700;
            }

            table {
            width: 80%;
            margin: 20px auto;
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
            text-decoration: none;
            }

            .previous-games-button:hover {
            background-color: #e6c200;
            }

            .new-game-button {
            position: absolute;
            top: 10px;
            right: 10px;
            padding: 9px 16px;
            background-color: #ffd700;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            color: #333;
            }

            .new-game-button:hover {
            background-color: #e6c200;
            }

            .resume-button {
            padding: 5px 10px;
            background-color: #ffd700;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            color: #333;
            }

            .resume-button:hover {
            background-color: #e6c200;
            }
        </style>
    </head>
    <body>
        <h1>Previous Games</h1>

        <a href="/spelling-bollie/start-game" class="new-game-button">Start New Game</a>

        <table>
            <thead>
                <tr>
                    <th>Game ID</th>
                    <th>Letters</th>
                    <th>Ranking</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <#list previousGames as game>
                    <tr>
                        <td>${game.id}</td>
                        <td>${game.letterSelection}</td>
                        <td>${game.ranking}</td>
                        <td><button class="resume-button" onclick="resumeSelectedGame('${game.id}')">Resume</button></td>
                    </tr>
                </#list>
            </tbody>
        </table>

        <script>
            function resumeSelectedGame(gameId) {
            const url = "/spelling-bollie/resume-game/" + gameId;

            window.location.href = url;
            }
        </script>
    </body>
</html>
