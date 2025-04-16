package main

import (
	"fmt"
	"os"
	"sync"
	"time"
)

func worker(id int, tasks <-chan Task, wg *sync.WaitGroup, mu *sync.Mutex, file *os.File) {
	defer wg.Done()
	for task := range tasks {
		fmt.Printf("[Worker %d] Processing %s\n", id, task.ID)
		time.Sleep(500 * time.Millisecond)

		mu.Lock()
		_, err := file.WriteString(task.ID + " processed\n")
		mu.Unlock()

		if err != nil {
			fmt.Printf("[Worker %d] Error: %v\n", id, err)
		} else {
			fmt.Printf("[Worker %d] Completed %s\n", id, task.ID)
		}
	}
}
