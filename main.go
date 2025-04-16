package main

import (
	"fmt"
	"os"
	"sync"
)

func main() {
	tasks := make(chan Task, 20)
	var wg sync.WaitGroup
	var mu sync.Mutex

	file, err := os.Create("results.txt")
	if err != nil {
		fmt.Println("Error creating file:", err)
		return
	}
	defer file.Close()

	for i := 1; i <= 5; i++ {
		wg.Add(1)
		go worker(i, tasks, &wg, &mu, file)
	}

	for i := 1; i <= 20; i++ {
		tasks <- Task{ID: fmt.Sprintf("Task-%d", i)}
	}
	close(tasks)

	wg.Wait()
	fmt.Println("All tasks completed.")
}
