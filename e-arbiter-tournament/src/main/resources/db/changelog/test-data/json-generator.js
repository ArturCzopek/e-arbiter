// it is a retarded code with "fake" functions which are called by www.json-generator.com
// be careful with dates, sometimes enddate for finished tournament may be weird...
// also, it would be good to replace task contents by yourself
[
    '{{repeat(20)}}',
    {
        'ownerId': '{{integer(1, 8)}}',
        'name': '{{lorem(1)}}',
        'description': '{{lorem(6)}}',
        'startDate': '{{date(new Date(2017, 6, 1), new Date(2017, 8, 1), "YYYY-MM-ddThh:mm:ss")}}',
        'endDate': '{{date(new Date(2017, 8, 2), new Date(2017, 10, 1), "YYYY-MM-ddThh:mm:ss")}}',
        'publicFlag': '{{bool()}}',
        'resultsVisibleForJoinedUsers': '{{bool()}}',
        'status': '{{random("DRAFT", "ACTIVE", "FINISHED")}}',
        'password': function () {
            if (!this.publicFlag) return "testPassword";
            return null;
        },
        'joinedUsersIds': function () {
            if (this.status === "DRAFT") return [];
            var min = 2;
            var max = 10;
            var users = Math.floor(Math.random() * (max - min + 1)) + min;

            var usersList = [];

            for (var i = 0; i < users; i++) {
                var userId = this.ownerId;

                while (usersList.indexOf(userId) !== -1 || userId === this.ownerId) {
                    userId = Math.floor(Math.random() * (20 - 1 + 1)) + 1;
                }
                usersList.push(userId);
            }

            return usersList;
        },
        'tasks': function () {
            var tasksAmount = Math.floor(Math.random() * (5 - 2 + 1)) + 2;

            var taskList = [];
            var languages = ["CPP", "C11", "JAVA", "PYTHON"];

            for (var i = 0; i < tasksAmount; i++) {

                var task = {
                    name: 'jiejrinewirnewnrew' + i,
                    description: 'jiejrinewirnewnrewjiejrinewirnewnrew' + i + 'jiejrinewirnewnrewjiejrinewirnewnrew' + i + 'jiejrinewirnewnrewjiejrinewirnewnrew' + i
                };


                var isCodeTask = Math.random() >= 0.5;
                if (isCodeTask) {
                    task.type = "CodeTask";

                    var codeTaskTestSets = [];

                    for (var j = 0; j < 5; j++) {
                        var expectedResult = Math.floor(Math.random() * (100 - 1 + 1)) + 5;
                        var parameters = [];

                        for (var k = 0; k < 4; k++) {
                            var param = Math.floor(Math.random() * (15 - 3 + 1)) + 3;
                            parameters.push("" + param);
                        }

                        var testSet = {
                            expectedResult: "" + expectedResult,
                            parameters: parameters
                        };

                        codeTaskTestSets.push(testSet);
                    }

                    task.codeTaskTestSets = codeTaskTestSets;
                    task.languages = [languages[Math.floor(Math.random() * languages.length)]];
                    task.timeoutInMs = Math.floor(Math.random() * (2000 - 5 + 1)) + 5;
                } else {
                    task.type = "QuizTask";

                    var questionsList = [];

                    for (var j = 0; j < 10; j++) {
                        var question = {};
                        question.content = 'jiwenri' + j;

                        var answersList = [];
                        for (var k = 0; k < 5; k++) {
                            answersList.push({content: 'iojiwenr' + k, correct: Math.random() >= 0.5})
                        }

                        question.answers = answersList;
                        questionsList.push(question);
                    }

                    task.questions = questionsList;
                }

                taskList.push(task);
            }

            return taskList;
        }
    }
]