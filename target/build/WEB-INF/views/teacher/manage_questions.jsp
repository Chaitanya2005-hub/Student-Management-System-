<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Manage Questions" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>❓ Question Bank & Exam Questions</h2>
        <p>Add 4-option MCQs to live exams or import questions from the Question Bank.</p>
    </div>

    <!-- Exam Selector -->
    <div class="glass-panel">
        <form action="${pageContext.request.contextPath}/teacher/manage-questions" method="get" style="display: flex; gap: 12px; align-items: center;">
            <label for="examSelect" style="margin: 0; font-weight: bold;">Select Exam:</label>
            <select id="examSelect" name="examId" class="form-select" style="max-width: 400px;" onchange="this.form.submit()">
                <option value="">-- Choose Exam --</option>
                <c:forEach var="ex" items="${exams}">
                    <option value="${ex.id}" ${selectedExam != null && selectedExam.id == ex.id ? 'selected' : ''}>
                        ${ex.title} (${ex.examDate})
                    </option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn--primary">Load Questions</button>
        </form>
    </div>

    <c:if test="${selectedExam != null}">
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 24px;">
            <!-- Create Question Form -->
            <div class="glass-panel">
                <h3>Add Question to ${selectedExam.title}</h3>
                <form action="${pageContext.request.contextPath}/teacher/manage-questions" method="post">
                    <input type="hidden" name="examId" value="${selectedExam.id}">
                    <div class="form-group">
                        <label for="questionText">Question Text</label>
                        <textarea id="questionText" name="questionText" class="form-control" rows="3" required placeholder="Enter the MCQ question text..."></textarea>
                    </div>
                    <div class="form-group">
                        <label for="optionA">Option A</label>
                        <input type="text" id="optionA" name="optionA" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="optionB">Option B</label>
                        <input type="text" id="optionB" name="optionB" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="optionC">Option C</label>
                        <input type="text" id="optionC" name="optionC" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="optionD">Option D</label>
                        <input type="text" id="optionD" name="optionD" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="correctAnswer">Correct Option</label>
                        <select id="correctAnswer" name="correctAnswer" class="form-select" required>
                            <option value="a">A</option>
                            <option value="b">B</option>
                            <option value="c">C</option>
                            <option value="d">D</option>
                        </select>
                    </div>
                    <div class="form-group" style="display: flex; align-items: center; gap: 10px;">
                        <input type="checkbox" id="saveToBank" name="saveToBank">
                        <label for="saveToBank" style="margin: 0;">Also save to central Question Bank</label>
                    </div>
                    <button type="submit" class="btn btn--primary" style="width: 100%;">Add Question</button>
                </form>
            </div>

            <!-- Existing Questions List & Bank Import -->
            <div>
                <!-- Search Bar -->
                <div class="search-bar-container">
                    <input type="text" id="qSearchInput" class="search-input" placeholder="🔍 Search current exam questions..." onkeyup="filterTable('qSearchInput', 'questionsTable')">
                    <button class="search-btn" onclick="filterTable('qSearchInput', 'questionsTable')">Search</button>
                </div>

                <div class="glass-panel">
                    <h3>Current Exam Questions (${questions.size()})</h3>
                    <div class="table-responsive">
                        <table class="glass-table" id="questionsTable">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Question</th>
                                    <th>Correct</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="q" items="${questions}" varStatus="st">
                                    <tr>
                                        <td>${st.index + 1}</td>
                                        <td>${q.questionText}</td>
                                        <td><span class="badge badge--active">${q.correctAnswer.toUpperCase()}</span></td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/teacher/manage-questions" method="post">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="questionId" value="${q.id}">
                                                <input type="hidden" name="examId" value="${selectedExam.id}">
                                                <button type="submit" class="btn btn--danger" style="padding: 4px 8px; font-size: 0.75rem;">Remove</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="glass-panel">
                    <h3>Import From Question Bank</h3>
                    <c:forEach var="bq" items="${bankQuestions}">
                        <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; margin-bottom: 8px; display: flex; justify-content: space-between; align-items: center;">
                            <div>
                                <strong style="font-size: 0.85rem;">[${bq.subject != null ? bq.subject : 'General'}]</strong> ${bq.questionText}
                            </div>
                            <form action="${pageContext.request.contextPath}/teacher/manage-questions" method="post">
                                <input type="hidden" name="action" value="import">
                                <input type="hidden" name="bankId" value="${bq.id}">
                                <input type="hidden" name="examId" value="${selectedExam.id}">
                                <button type="submit" class="btn btn--primary" style="padding: 4px 8px; font-size: 0.75rem;">+ Import</button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../common/footer.jsp" />
