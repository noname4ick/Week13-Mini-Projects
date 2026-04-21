# Topic: JavaFX — Mini Projects
## Problem 1
(Personal Expense Tracker) Build ExpenseTracker.java — a small desktop app that
lets a user log daily expenses and review them later. This kind of tool is directly useful for
personal budgeting or as a base for any data-entry application.
1. Create a GridPane form (hgap 10, vgap 12, padding 20) with the following Label/TextField
pairs: Category (e.g. Food, Transport), Amount, and Note (optional description).
2. An Add Expense button reads all three fields with getText().trim(), validates that
Category and Amount are not empty, parses Amount to double inside a try-catch,
then appends one line to expenses.txt in the format category|amount|note using
BufferedWriter in append mode. Show a green "Saved!" label on success and a red
error label on failure.
3. A Show Summary button reads expenses.txt line by line, splits on "|", accumu-
lates the total amount, and displays in a non-editable TextArea: each expense on its own
line as "Food --- $12.50 (lunch)", followed by a final line "Total: $XX.XX".
4. A Clear Fields button calls .clear() on all input fields and resets the status label.
5. Arrange the form, buttons, status label, and summary area in a VBox (spacing 12,
padding 20). Set the window title to "Expense Tracker" and scene size to 460×420.
Expected output:
Page 2After adding three expenses the summary area shows each entry and a running total.
The file persists between runs so expenses from previous sessions appear too.
## Problem 2
(Flashcard Study App) Build FlashcardApp.java — a revision tool where cards are
stored in a plain text file and the user flips through them one at a time. This is a real-world
study utility you can extend with your own subject matter.
1. The file cards.txt stores one card per line in the format question|answer. Pre-
populate it with at least five cards on any topic before running (e.g. Java keywords and
their meanings).
2. On startup, read all cards from cards.txt into an ArrayList of string arrays. If
the file is missing or empty, display "No cards found. Add cards to cards.txt
and restart." and stop.
3. Display the question side of the current card in a Label (bold, font size 20, centred,
wrapped). Show the card index as "Card 1 / 5" in a smaller label above it.
4. A Flip button toggles the label between the question and the answer. The label back-
ground changes colour: light blue for question, light green for answer (use setStyle).
5. A Next button advances to the next card (wraps back to card 1 after the last) and
resets the view to show the question side. A Previous button goes back one card.
6. Arrange everything in a VBox (spacing 16, padding 30, centred). Scene size 480×300.
Expected output:
## Problem 3
(Countdown Timer) Build CountdownTimer.java — a fully functional countdown timer
with start, pause, and reset controls. A Timeline drives the countdown and a FadeTransition
draws attention when time runs out. This is a reusable component for any productivity or
exam tool.
1. Provide a TextField where the user enters the number of minutes. A Start button
reads the value with getText().trim(), parses it to int (show an error label if invalid
or ≤ 0), and begins the countdown.
2. Display the remaining time in a large Label (font size 52, bold, monospaced) formatted
as MM:SS, e.g. 05:00. Update this label inside a Timeline KeyFrame that fires every
1 second and decrements a remainingSeconds counter.
3. A Pause / Resume button calls timeline.pause() when the timer is running and
timeline.play() when it is paused, checking timeline.getStatus() == Animation.Status.RUN
4. A Reset button stops the timeline, resets remainingSeconds to zero, and restores the
display to 00:00.
5. When remainingSeconds reaches zero, stop the Timeline and apply a FadeTransition
(0.5 seconds, opacity 1.0 → 0.1, autoReverse true, INDEFINITE) to the time label, and
change its colour to red to signal that time is up.
6. Arrange everything in a VBox (spacing 16, padding 30, centred). Scene size 340×260.
## Problem 4
(Mini Contact Book) Build ContactBook.java — a two-scene application where contacts
are stored in a file and can be browsed, added, and deleted. Scene switching, file I/O, and
event handling all come together in one self-contained mini project.
1. Scene 1 — Contact List. Display all contacts stored in contacts.txt inside
a ListView<String> (each item shown as "Name --- phone@email"). Provide an
Add New button that switches to Scene 2. Provide a Delete Selected button that
removes the selected line from contacts.txt (rewrite the whole file without that line)
and refreshes the list. A Refresh button re-reads the file and repopulates the list.
2. Scene 2 — Add Contact. Show a GridPane form with fields for Name, Phone, and
Email. A Save button validates that no field is empty, then appends name|phone|email
to contacts.txt using BufferedWriter in append mode and switches back to Scene
1. A Cancel button returns to Scene 1 without saving.
3. Store the Stage as a field so both scenes can call stage.setScene(...) to navigate.
4. The contact list must refresh automatically each time Scene 1 becomes active (call your
load method from the Add contact’s Save button before switching back).
5. Handle all IOExceptions with a visible error Label. Scene size for both scenes:
480×380.
