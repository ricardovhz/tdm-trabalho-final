import { Component } from '@angular/core';
import { AngularFirestore, AngularFirestoreDocument } from 'angularfire2/firestore';
import { map } from 'rxjs/operators'
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public tasks: Observable<any[]>;
  public newDescription: string

  constructor(private db: AngularFirestore) {
    this.newDescription = "";
    this.tasks = db.collection("tasks").snapshotChanges().pipe(
      map(actions => actions.map(a => {
        const d = a.payload.doc.data() as any;
        const id = a.payload.doc.id;
        return { id, description: d.description, dueDate: d.dueDate, done: d.done };
      })));
  }

  changeTaskDone(t: any) {
    this.db.doc('tasks/'+t.id)
      .set({description: t.description, dueDate: t.dueDate, done: !t.done});
  }

  add() {
    const d = this.newDescription;
    this.newDescription = "";
    this.db.doc("tasks/"+this.guid()).set({description: d, dueDate:new Date()})
  }

  private guid() {
    function s4() {
      return Math.floor((1 + Math.random()) * 0x10000)
        .toString(16)
        .substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
  }
  
}
